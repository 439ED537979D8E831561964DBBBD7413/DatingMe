package com.acemurder.datingme.data.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.BuildConfig;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.data.network.function.ResultWrapperFunc;
import com.acemurder.datingme.data.network.interceptors.HeaderInterceptors;
import com.acemurder.datingme.data.network.service.LeanCloudApiService;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.avos.avoscloud.AVUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import cn.leancloud.chatkit.LCChatKitUser;
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

    public Subscription date(Subscriber<Response> subscriber, DatingItem item, AVUser user) {
        if (item.getPromulgatorId().equals(user.getObjectId())) {
            throw new IllegalArgumentException("Promulgator and Receiver cant't be a same one.");
        } else {
            try {
                DatingItem itemCopy = (DatingItem) item.clone();
                itemCopy.setReceiver(user.getUsername());
                itemCopy.setReceiverId(user.getObjectId());
                itemCopy.setHasDated(true);
                // itemCopy.setReceiverPhoto(user.getPhotoSrc());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new JSONObject(itemCopy.toString()).toString());
                //  Log.e("xxxxxxxxxx",)
                Observable<Response> observable = mApiService.date(item.getObjectId(), body);

                return emitObservable(observable, subscriber);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public Subscription login(Subscriber<User> subscriber, String userName, String password) {
        Observable<User> observable = mApiService.login(userName, password);
        return emitObservable(observable, subscriber);

    }


    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber, int page, int count) {

        Observable<List<DatingItem>> observable = mApiService.getDatingItems(page + "", count + "", "-createdAt").map(new ResultWrapperFunc<List<DatingItem>>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getCommunityItems(Subscriber<List<Community>> subscriber, int page, int count) {

        Observable<List<Community>> observable = mApiService.getCommunityItems(page + "", count + "", "-updatedAt").map(new ResultWrapperFunc<List<Community>>());
        return emitObservable(observable, subscriber);

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


    public Subscription getAllLCChatKitUser(Subscriber<List<LCChatKitUser>>subscriber){
        Observable<List<LCChatKitUser>> observable = mApiService
                .getAlluser().map(new ResultWrapperFunc<>())
                .map(new Func1<List<User>, List<LCChatKitUser>>() {
                    @Override
                    public List<LCChatKitUser> call(List<User> users) {
                        List<LCChatKitUser> newUsers = new ArrayList<LCChatKitUser>();
                        for (User user:users){
                            if (user.getPhotoSrc().equals("null"))
                                user.setPhotoSrc("http://image.acemurder.com/DatingMe/moiling.jpg");
                            newUsers.add(new LCChatKitUser(user.getObjectId(),user.getUsername(),user.getPhotoSrc()));
                        }
                        return newUsers;
                    }
                });
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
        Observable<List<Remark>> observable = mApiService.getRemarkItems(data, "-updatedAt").map(new ResultWrapperFunc<List<Remark>>());
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
