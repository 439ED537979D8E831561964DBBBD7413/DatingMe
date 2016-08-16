package com.acemurder.datingme.data.bean;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class ResultWrapper<T>{
    private T results;

    public T getResults() {
        return results;
    }

    public void setResult(T results) {
        this.results = results;
    }
}
