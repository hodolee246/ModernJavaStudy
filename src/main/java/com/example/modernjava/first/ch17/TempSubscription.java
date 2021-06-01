package com.example.modernjava.first.ch17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.*;

public class TempSubscription implements Subscription {

    // 스택오버플로 해결용 Executor
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    @Override
    public void request(long n) {
        // 다른 스레드에서 다음 요소를 구독자에게 전송한다.
//        executor.submit( () -> {
            // Subscriber가 만든 요청을 하나씩 반복한다.
            for(long i = 0L; i < n; i++) {
                try {
                    // 현재 온도를 Subscriber에게 전달
                    subscriber.onNext(TempInfo.fetch(town));
                } catch (Exception e) {
                    // error 발생시 Subscriber에게 에러 전송
                    subscriber.onError(e);
                    break;
                }
            }
//        });
    }

    @Override
    public void cancel() {
        // 구독이 취소되면 완료 신호를 Subscription에게 전송
        subscriber.onComplete();
    }
}
