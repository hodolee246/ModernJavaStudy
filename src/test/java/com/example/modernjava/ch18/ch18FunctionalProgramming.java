package com.example.modernjava.ch18;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

@Slf4j
public class ch18FunctionalProgramming {

    // 반복 팩토리얼
    public static int factorialIterator(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
            log.info("r : {}, i : {}", r, i);
        }
        return r;
    }
    // 재귀 팩토리얼
    public static long factorialRecursive(long n) {
        log.info("n : {}", n);
        return n == 1 ? 1 : n * factorialRecursive(n-1);
    }
    // 스트림 팩토리얼
    public static long factorialStreams(long n) {
        log.info("n : {}", n);
        return LongStream.rangeClosed(1, n)
                            .reduce(1, (long a, long b) -> a * b);
    }
    // 꼬리 재귀 팩토리얼
    public static long factorialTailRecursive(long n) {
        log.info("n : {}", n);
        return factorialHelper(1, n);
    }
    // 꼬리 재귀 팩토리얼 헬퍼
    public static long factorialHelper(long acc, long n) {
        log.info("acc : {}, n : {}", acc, n);
        return n == 1 ? acc : factorialHelper(acc * n, n-1);
    }

    @Test
    public void factorial() {
        int iterator = factorialIterator(3);
        long recursive = factorialRecursive(3);
        long streams = factorialStreams(3);
        long tailRecursive = factorialTailRecursive(3);
        log.info("iterator : {}, recursive : {}, streams : {}, tailRecursive : {}", iterator, recursive, streams, tailRecursive);
    }
}
