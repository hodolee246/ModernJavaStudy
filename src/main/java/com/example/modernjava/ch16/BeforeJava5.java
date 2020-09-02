package com.example.modernjava.ch16;

import java.util.concurrent.*;

public class BeforeJava5 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                // 오래걸리는 작업은 다른 스레드에서 실행되게 (실제로는 null이 아님)
                return null;
            }
        });
        try {
            // 블록 방지 ( 1초간 대기 )
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            // 계산 중 예외 발생
        } catch (InterruptedException e2) {
            // 현재 스레드에서 대기 중 인터럽트 발생
        } catch (TimeoutException e3) {
            // Future가 완료되지 전에 타임아웃 발생
        }
    }
}
