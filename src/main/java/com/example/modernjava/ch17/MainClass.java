package com.example.modernjava.ch17;

import java.util.concurrent.Flow.*;

public class MainClass {
    public static void main(String[] args) {
        // 뉴욕에 새 Publisher를 만들고 TempSubscriber를 구독시킴
//        getTemperatures("New York").subscribe(new TempSubscriber());
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }
    // 구독한 Subscriber에게 TempSubscrition을 전송하는 Publisher를 반환
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
}
