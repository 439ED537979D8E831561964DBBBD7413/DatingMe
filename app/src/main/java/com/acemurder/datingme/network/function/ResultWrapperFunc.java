package com.acemurder.datingme.network.function;

import com.acemurder.datingme.bean.ResultWrapper;

import rx.functions.Func1;

/**
 * Created by zhengyuxuan on 16/8/16.
 */

public class ResultWrapperFunc<T> implements Func1<ResultWrapper<T>,T> {
    @Override
    public T call(ResultWrapper<T> tResultWrapper) {
        if (tResultWrapper.getResult() != null){
            return tResultWrapper.getResult();
        }else
            throw new RuntimeException("LeanCloud Error");
    }
}
