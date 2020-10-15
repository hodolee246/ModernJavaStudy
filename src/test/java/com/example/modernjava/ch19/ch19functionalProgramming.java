package com.example.modernjava.ch19;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ranges.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

@Slf4j
public class ch19functionalProgramming {

    // 커링이란 한 개의 인수를 갖는 변환 함수를 생성하는 팩토리 코드이다.

    // x : 변환하려는 값
    // f : 변환 요소
    // b : 기준치 조정 요소
    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    // 기존 로직(위)을 이용하여 커링이라는 개념을 활용하녀 한 개의 인수를 갖는 변환 함수를 생성하는 팩토리 정의코드
    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
    }

    DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
    DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
    DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

    // DoubleUnaryOperator는 applyAsDouble 이라는 메서드를 정의하므로 다음처럼 변환기를 사용할 수 있다.
    @Test
    public void curringTest() {
        double temperature = convertCtoF.applyAsDouble(32);
        double gbp = convertUSDtoGBP.applyAsDouble(500);
        double mile = convertKmtoMi.applyAsDouble(1000);

        log.info("temperature : {} / gbp : {} / mile : {}", temperature, gbp, mile);
    }

    // 파괴적인 갱신과 함수형
    // 귀찬아서...
    @Getter
    @ToString
    @NoArgsConstructor
    static class TrainJourney {
        public int price;
        public TrainJourney onward;
        public TrainJourney(int p, TrainJourney t) {
            price = p;
            onward = t;
        }
    }
    // 파괴적인 갱신 == 왜??????
    static TrainJourney link(TrainJourney a, TrainJourney b) {
        if (a == null)
            return b;
        TrainJourney t = a;
        while(t.onward != null) {
            t = t.onward;
        }
        t.onward = b;
        return a;
    }
    // 함수적
    static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.price, append(a.onward, b));
    }

    // 테스트 어떻게함??
    @Test
    public void destroyFunctional() {
        TrainJourney number1 = new TrainJourney();
        TrainJourney bSite = new TrainJourney();
        TrainJourney aSite = new TrainJourney(1000, number1);
        TrainJourney newSite = link(aSite, bSite);

        log.info("newSite : {}", newSite);
        log.info("newSite.getOnward() : {}", newSite.getOnward());
        log.info("newSite.getOnward().getOnward() : {}", newSite.getOnward().getOnward());
    }

    @Setter
    static class Tree {
        private String key;
        private int val;
        private Tree left, right;
        public Tree(String k, int v, Tree l, Tree r) {
            key = k; val = v; left = l; right = r;
        }
    }

    static class TreeProcessor {
        public static int lookup(String k, int defaultval, Tree t) {
            if(t == null)
                return defaultval;
            if(k.equals(t.key))
                return t.val;
            return lookup(k, defaultval, k.compareTo(t.key) < 0 ? t.left : t.right);
        }
    }

    /*static void update(String k, int newval, Tree t) {
        if (k.equals(t.key))
            t.val = newval;
        else update(k, newval, k.compareTo(t.key) < 0 ? t.left : t.right);
    }*/

    // 캐싱
    // 인자값이 동일하다면 반환값이 같은 참조투명성은 유지되지만 실질적으로 함수형프로그래밍은 아니다.
    final Map<Object, Integer> numberOfNodes = new HashMap<>();
    Integer computeNumberOfNodesUsingCash(String c) {
        Integer result = numberOfNodes.get(c);
        if(result != null) {
            log.info("not null");
            return result;
        }
        result = 5;
        numberOfNodes.put(c, result);
        return result;
    }

    @Test
    public void computeNumber() {
        Integer firstNum = computeNumberOfNodesUsingCash("abc");
        Integer secondNum = computeNumberOfNodesUsingCash("abc");

        log.info("firstNum : {}, secondNum : {}", firstNum, secondNum);
    }
}
