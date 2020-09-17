package com.example.modernjava.ch17;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ch17ReactiveProgrammingTempInfo {

    @Test
    public void subscribeTempInfo() {
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    @Test
    public void subscribeProcessTempInfo() {
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }

    private static Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public static Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }

    @Test
    public void observable() {
        // just() 팩토리 메서드는 Observable의 구독자에게 onNext("first"), onNext("second"), onComplete()의 순서로 메시지를 받는다.
        Observable<String> strings = Observable.just("first", "second");
        // 0부터 시작해 1초 간격으로 long 형식의 값을 무한으로 증가시키며 값을 방출한다.
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);

        onePerSec.blockingSubscribe(i -> log.info("value : {}", TempInfo.fetch("USA")));
    }

    @Test
    public void observableTemperature() {
        getTemperature("New York").blockingSubscribe(new TempObserver());
    }
    private static Observable<TempInfo> getTemperature(String town) {
        // Observer를 소비하는 함수로부터 Observable 만들기
        return Observable.create(emitter ->
                // 매 초마다 무한으로 증가하는 long값을 방출하는 Observable
                Observable.interval(1, TimeUnit.SECONDS)
                        .subscribe(i -> {
                            // 소비된 Observer가 아직 폐기되지 않았으면 작업 수행
                            if(!emitter.isDisposed()) {
                                if (i >= 5) { // 5번 전송 시
                                    // 종료
                                    emitter.onComplete();
                                } else {
                                    try {
                                        // 5번 전송하지 않았다면 온도를 보고
                                        emitter.onNext(TempInfo.fetch(town));
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }
                        })
        );
    }

}

