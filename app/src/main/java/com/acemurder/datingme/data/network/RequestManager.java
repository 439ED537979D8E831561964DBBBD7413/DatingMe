package com.acemurder.datingme.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.BuildConfig;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.data.network.function.CommunityFunc;
import com.acemurder.datingme.data.network.function.DatingItemFunc;
import com.acemurder.datingme.data.network.function.RemarkFunc;
import com.acemurder.datingme.data.network.function.ResultWrapperFunc;
import com.acemurder.datingme.data.network.interceptors.HeaderInterceptors;
import com.acemurder.datingme.data.network.service.LeanCloudApiService;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.avos.avoscloud.AVUser;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


import static android.R.attr.id;
import static android.R.attr.key;
import static com.acemurder.datingme.config.Const.endpoint;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public enum RequestManager {


    INSTANCE;
    private LeanCloudApiService mApiService;
    private OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 30;
    private OSSClient oss;
//    private OSSClient ossClient;


    RequestManager() {
        mOkHttpClient = configureOkHttp(new OkHttpClient.Builder());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(LeanCloudApiService.class);
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Const.accessKeyId, Const.accessKeySecret);

        oss = new OSSClient(APP.getContext(), endpoint, credentialProvider);

    }

    public Subscription date(Subscriber<Response> subscriber, DatingItem item) {

            try {
                DatingItem itemCopy = (DatingItem) item.clone();
                itemCopy.setReceiver(APP.getAVUser().getUsername());
                itemCopy.setReceiverId(APP.getAVUser().getObjectId());
                itemCopy.setHasDated(true);
                User user = new User();
                user.setObjectId(APP.getAVUser().getObjectId());
                itemCopy.setRecipietn(user);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(itemCopy.toString()).toString());
                Observable<Response> observable = mApiService.date(item.getObjectId(), body);
                return emitObservable(observable, subscriber);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

    }


    public Subscription login(Subscriber<User> subscriber, String userName, String password) {
        Observable<User> observable = mApiService.login(userName, password);
        return emitObservable(observable, subscriber);

    }

    public Subscription getDatingItemsWithName(SimpleSubscriber<List<DatingItem>>subscriber,String name){
        String data = "{\""+"promulgator\":"+ "\""+name +"\"}";
        Log.e(".........",data);
        Observable<List<DatingItem>> observable = mApiService.getMyDatingItem(data,"-createdAt","master,recipietn").map(new DatingItemFunc());
        return emitObservable(observable,subscriber);

    }

    public Subscription getRecieveDatingItemsWithName(SimpleSubscriber<List<DatingItem>>subscriber,String name){
        String data = "{\""+"receiver\":"+ "\""+name +"\"}";

        Observable<List<DatingItem>> observable = mApiService.getMyDatingItem(data,"-createdAt","master,recipietn").map(new DatingItemFunc());
        return emitObservable(observable,subscriber);
    }


    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber, int page, int count) {

        //Observable<List<DatingItem>> observable = mApiService.getDatingItems(page + "", count + "", "-createdAt").map(new DatingItemFunc());
        return getDatingItems(subscriber,page,count,"master,recipietn");
    }



    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber, int page, int count,String include) {
        Observable<List<DatingItem>> observable = mApiService.getDatingItems(page + "", count + "", "-createdAt",include).map(new DatingItemFunc());
        return emitObservable(observable, subscriber);
    }

    public Subscription getCommunityItems(Subscriber<List<Community>> subscriber, int page, int count) {

        Observable<List<Community>> observable = mApiService.getCommunityItems(page + "", count + "", "-updatedAt","master").map(new CommunityFunc());
        return emitObservable(observable, subscriber);

    }

    public Subscription updateUser(Subscriber<Response>subscriber, String id,String introduction){
        String data = "{\"description\":\""+introduction+"\"}";
        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(data)).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response>observable = mApiService.updateUser(id,body);
        return emitObservable(observable,subscriber);
    }

    public Subscription updateUser(Subscriber<Response>subscriber, String id,
                                   String introduction,String imagePath){
        final String key = "DatingMe/face/" + APP.getAVUser().getObjectId() + "_" + System.currentTimeMillis() + new File(imagePath).getName();

        PutObjectRequest put = new PutObjectRequest("acemurder", key, imagePath);
        String photo = "http://image.acemurder.com/" + key;
        String data = "{\"description\":\""+introduction+"\",\"photoSrc\":\""+photo+"\"}";

        RequestBody body = null;


        Observable<Response> observable = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = new Response(oss.putObject(put));
                    subscriber.onNext(response);

                } catch (ClientException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });
        try {
            return emitObservable(observable
                    .zipWith(mApiService.updateUser(id,RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(data)).toString())),
                            Response::cloneFromResult), subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Subscription getUserInfo(Subscriber<User>subscriber,String id){
        Observable<User>observable = mApiService.getUserInfo(id);
        return emitObservable(observable,subscriber);
    }


    public Subscription addDatingItem(Subscriber<Response> subscriber, DatingItem datingItem, final String imagePath) {
        final String key = "DatingMe/datingItem/" + APP.getAVUser().getObjectId() + "_" + System.currentTimeMillis() + new File(imagePath).getName();
        PutObjectRequest put = new PutObjectRequest("acemurder", key, imagePath);
        datingItem.setPhotoSrc("http://image.acemurder.com/" + key);

        Observable<Response> observable = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = new Response(oss.putObject(put));
                    subscriber.onNext(response);

                } catch (ClientException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });
        try {
            return emitObservable(observable
                    .zipWith(mApiService.addDatingItem(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(datingItem.toString())).toString())),
                            Response::cloneFromResult), subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


    public Subscription addDatingItem(Subscriber<Response> subscriber, String data) {
        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(data)).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addDatingItem(body);
        return emitObservable(observable, subscriber);
    }

    public Subscription getAllUser(Subscriber<List<User>> subscriber){
        return emitObservable(mApiService.getAlluser().map(new ResultWrapperFunc<>()),subscriber);
    }





    //增加一条社区动态,无图
    public Subscription addCommunityItem(Subscriber<Response> subscriber, Community community) {

        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(community.toString())).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addCommunityItem(body);
        return emitObservable(observable, subscriber);
    }


    //增加一条社区动态,带图
    public Subscription addCommunityItem(Subscriber<Response> subscriber, Community community,List<String> imagePath) {
       // final String key = "DatingMe/CommunityItem/" + APP.getAVUser().getObjectId() + "_" + System.currentTimeMillis() + new File(imagePath).getName();
       // PutObjectRequest put = new PutObjectRequest("acemurder", key, imagePath);
        final String key = "DatingMe/CommunityItem/" + APP.getAVUser().getObjectId() + "_" + System.currentTimeMillis() + "_"+new File(imagePath.get(0)).getName();
        PutObjectRequest put = new PutObjectRequest("acemurder", key, imagePath.get(0));
        List<String> list = new ArrayList<>();
        list.add("http://image.acemurder.com/"+key);
        community.setPhotoSrc(list);

        Observable<Response> observable = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = new Response(oss.putObject(put));
                    subscriber.onNext(response);

                }catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
        });
        try {
            return emitObservable(observable
                    .zipWith(mApiService.addCommunityItem(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(community.toString())).toString())),
                            Response::cloneFromResult), subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }

    public Subscription getRemarkItems(Subscriber<List<Remark>> subscriber, String communityId) {
        //{"objectId":"57b02f507db2a20054238cb3"}
        String data = "{\"communityId\":\"" + communityId + "\"}";
        Observable<List<Remark>> observable = mApiService.getRemarkItems(data, "-updatedAt","master").map(new RemarkFunc());
        return emitObservable(observable, subscriber);
    }

    public Subscription sendRemark(Subscriber<Response> subscriber, String data) {
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(data).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addRemarkItem(body,"-updatedAt");
        return emitObservable(observable, subscriber);
    }


    private <T> Subscription emitObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private OkHttpClient configureOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HeaderInterceptors headerInterceptors = new HeaderInterceptors();
        builder.addInterceptor(headerInterceptors);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }


}
