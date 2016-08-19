package com.acemurder.datingme.data.network;

import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.BuildConfig;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.data.network.function.ResultWrapperFunc;
import com.acemurder.datingme.data.network.interceptors.HeaderInterceptors;
import com.acemurder.datingme.data.network.service.LeanCloudApiService;
import com.alibaba.fastjson.parser.deserializer.JSONObjectDeserializer;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
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
import rx.schedulers.Schedulers;

import static android.R.attr.data;
import static com.acemurder.datingme.config.Const.accessKeyId;
import static com.acemurder.datingme.config.Const.accessKeySecret;
import static com.acemurder.datingme.config.Const.endpoint;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public enum RequestManager {


    INSTANCE;
    private LeanCloudApiService mApiService;
    private OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 30;
    OSS oss;
//    private OSSClient ossClient;


    RequestManager() {
        mOkHttpClient = configureOkHttp(new OkHttpClient.Builder());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    //    ossClient = new OSSClient("acemurder", Const.accessKeyId, Const.accessKeySecret);
// 上传文件
// 关闭client
        mApiService = retrofit.create(LeanCloudApiService.class);
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Const.accessKeyId, Const.accessKeySecret);

        oss = new OSSClient(APP.getContext(), endpoint, credentialProvider);

    }


    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber, int page, int count) {

        Observable<List<DatingItem>> observable = mApiService.getDatingItems(page + "", count + "").map(new ResultWrapperFunc<List<DatingItem>>());
        return emitObservable(observable, subscriber);
    }

    public Subscription getCommunityItems(Subscriber<List<Community>> subscriber, int page, int count) {

        Observable<List<Community>> observable = mApiService.getCommunityItems(page + "", count + "").map(new ResultWrapperFunc<List<Community>>());
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

    public Subscription addDatingItem(Subscriber<Response> subscriber, DatingItem datingItem, final String imagePath) {
        final String key = "DatingMe/datingItem/"+APP.getAVUser().getObjectId()+"_"+System.currentTimeMillis()+new File(imagePath).getName();
        PutObjectRequest put = new PutObjectRequest("acemurder", key, imagePath);
        datingItem.setPhotoSrc("image.acemurder.com/"+key);

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
            Log.e("..........",datingItem.getPhotoSrc());
            return emitObservable(observable
                    .zipWith(mApiService.addDatingItem(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(datingItem.toString())).toString())),
                    Response::cloneFromResult),subscriber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
       /* oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                RequestBody body = null;
                try {
                    body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(data)).toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Observable<Response> observable = mApiService.addDatingItem(body);
                //return emitObservable(observable, subscriber);
            }

            @Override
            public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {

            }
        })*/

    }

    public Subscription addCommunityItem(Subscriber<Response> subscriber, String data) {
        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(data)).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addCommunityItem(body);
        return emitObservable(observable, subscriber);
    }

    public Subscription getRemarkItems(Subscriber<List<Remark>> subscriber, String communityId) {
        //{"objectId":"57b02f507db2a20054238cb3"}
        String data = "{\"communityId\":\"" + communityId + "\"}";
        Observable<List<Remark>> observable = mApiService.getRemarkItems(data).map(new ResultWrapperFunc<List<Remark>>());
        return emitObservable(observable, subscriber);
    }

    public Subscription sendRemark(Subscriber<Response> subscriber,String data){
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new JSONObject(data).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addRemarkItem(body);
        return  emitObservable(observable,subscriber);
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
