package com.example.modernjava.ch17;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow.*;

@Slf4j
public class TempSubscriber implements Subscriber<TempInfo> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1L);
    }

    @Override
    public void onNext(TempInfo tempInfo) {
        // 수신정보 출력 후 재 요청
        log.info("TempInfo: {}", tempInfo);
        subscription.request(1L);
    }

    @Override
    public void onError(Throwable throwable) {
        // 에러가 발생할 경우
        log.error(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        // 전부 종료되면
        log.info("Done!!");
    }
}
