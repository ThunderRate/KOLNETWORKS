package com.kolnetworks.koln.api;

import android.telephony.SubscriptionManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class MyObserver<T> implements Observer<T> {

    private Disposable d;

    @Override
    public void onSubscribe(Disposable d) {


    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(ExceptionHandle.handleException(e));

    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFail(ExceptionHandle.ResponseThrowable throwable);
}
