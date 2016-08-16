package com.acemurder.datingme.network.subscriber;

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
