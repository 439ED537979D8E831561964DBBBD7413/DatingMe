package com.acemurder.datingme.data.network.subscriber;

public abstract class SubscriberListener<T> {

    public void onStart() {
    }

    public void onCompleted() {
    }

    public void onError(Throwable e) {
    }

    public void onNext(T t) {
    }

}
