package com.example.modernjava.ch16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1));
    }
    // 인위적으로 지연시키는 메서드
    public static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }
    // 이 친구가 실행이 되려면 결국에는 1초동안 잠을 자다가 값을 구한다... 굿나잇..
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    // 동기 -> 비동기
    public Future<Double> getPriceAsync(String product) {
//        // 계산이 끝나면 결과를 리턴이 가능한 future를 이용하여
//        CompletableFuture<Double> completableFuture = new CompletableFuture<>();
//        // thread 에 계산식을 대입한다. (원하는 비동기)
//        new Thread(() -> {
//            try {
//                double pricte = calculatePrice(product);
//                // 계산이 끝나면 다른 Thread 에 반영을 한다.
//                completableFuture.complete(pricte);
//            } catch (Exception e) {
//                completableFuture.completeExceptionally(e);
//            }
//        }).start();
//        // 계산결과를 기다리지 않고 바로 return 해준다.
//        return completableFuture;
        // 위에 내용을 아래와 같이 팩토리메서드 supplyAsync로 만들 수 있다.
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getName() {
        return name;
    }
}

