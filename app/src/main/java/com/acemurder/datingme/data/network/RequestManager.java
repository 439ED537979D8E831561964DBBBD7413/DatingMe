package com.acemurder.datingme.data.network;

import com.acemurder.datingme.BuildConfig;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.data.network.function.ResultWrapperFunc;
import com.acemurder.datingme.data.network.interceptors.HeaderInterceptors;
import com.acemurder.datingme.data.network.service.LeanCloudApiService;


import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                .addConverterFactory(
                        GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(LeanCloudApiService.class);

    }

    private OkHttpClient configureOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HeaderInterceptors headerInterceptors = new HeaderInterceptors();
        builder.addInterceptor(headerInterceptors);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public Subscription getDatingItems(Subscriber<List<DatingItem>> subscriber,int page,int count){

        Observable<List<DatingItem>> observable = mApiService.getDatingItems(page+"",count+"").map(new ResultWrapperFunc<List<DatingItem>>());
        return emitObservable(observable,subscriber);

        //return  emitObservable(mApiService.getDatingItems(page+"",count+""),subscriber);
    };


    private <T> Subscription emitObservable(Observable<T> o, Subscriber<T> s) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
