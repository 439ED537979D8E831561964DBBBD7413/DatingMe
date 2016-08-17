package com.acemurder.datingme.data.network;

import com.acemurder.datingme.BuildConfig;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.function.ResultWrapperFunc;
import com.acemurder.datingme.data.network.interceptors.HeaderInterceptors;
import com.acemurder.datingme.data.network.service.LeanCloudApiService;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public enum RequestManager {


    INSTANCE;
    private LeanCloudApiService mApiService;
    private OkHttpClient mOkHttpClient;
    private static final int DEFAULT_TIMEOUT = 30;


    RequestManager(){
        mOkHttpClient = configureOkHttp(new OkHttpClient.Builder());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(LeanCloudApiService.class);

    }



    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber,int page,int count){

        Observable<List<DatingItem>> observable = mApiService.getDatingItems(page+"",count+"").map(new ResultWrapperFunc<List<DatingItem>>());
        return emitObservable(observable,subscriber);
    }

    public Subscription getCommunityItems(Subscriber<List<Community>> subscriber,int page,int count){

        Observable<List<Community>> observable = mApiService.getCommunityItems(page+"",count+"").map(new ResultWrapperFunc<List<Community>>());
        return emitObservable(observable,subscriber);

    }

    public Subscription addDatingItem(Subscriber<Response> subscriber ,String data){
        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(data)).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addDatingItem(body);
        return emitObservable(observable,subscriber);
     }

    public Subscription addCommunityItem(Subscriber<Response> subscriber ,String data){
        RequestBody body = null;
        try {
            body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(data)).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Response> observable = mApiService.addCommunityItem(body);
        return emitObservable(observable,subscriber);
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
