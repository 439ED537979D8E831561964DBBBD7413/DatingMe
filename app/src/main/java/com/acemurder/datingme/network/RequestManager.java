package com.acemurder.datingme.network;

import com.acemurder.datingme.bean.DatingItem;
import com.acemurder.datingme.network.function.ResultWrapperFunc;
import com.acemurder.datingme.network.service.LeanCloudApiService;

import java.util.List;

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

    RequestManager(){

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
