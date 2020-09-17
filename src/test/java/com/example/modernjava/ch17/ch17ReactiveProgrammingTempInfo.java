package com.example.modernjava.ch17;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;

public class ch17ReactiveProgrammingTempInfo {

    @Test
    public void tempInfo() {
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    @Test
    public void ProcessTempInfo() {
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }

    private static Flow.Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    public static Flow.Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }


}
