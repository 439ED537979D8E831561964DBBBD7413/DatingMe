package com.acemurder.datingme.data.network.function;

import android.util.Log;

import com.acemurder.datingme.data.bean.ResultWrapper;

import rx.functions.Func1;

/**
 * Created by zhengyuxuan on 16/8/16.
 */

public class ResultWrapperFunc<T> implements Func1<ResultWrapper<T>,T> {
    @Override
    public T call(ResultWrapper<T> tResultWrapper) {
        Log.e("=====",(tResultWrapper == null)+"");

        if (tResultWrapper.getResults() != null){
            return tResultWrapper.getResults();
        }else{
            throw new RuntimeException("LeanCloud Error");

        }
    }
}
