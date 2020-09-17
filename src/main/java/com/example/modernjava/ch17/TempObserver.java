package com.example.modernjava.ch17;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TempObserver implements Observer<TempInfo> {
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {

    }

    @Override
    public void onNext(@NonNull TempInfo tempInfo) {
        log.info("tempInfo : {}", tempInfo);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        log.error("error : {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("done");
    }
}
