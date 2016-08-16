package com.acemurder.datingme.data.bean;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class ResultWrapper<T>{
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
