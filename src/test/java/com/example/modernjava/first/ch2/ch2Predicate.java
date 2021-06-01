package com.example.modernjava.first.ch2;

import com.example.modernjava.first.ch2.predicate.ApplePredicate;
import com.example.modernjava.first.ch2.predicate.Predicate;
import com.example.modernjava.first.vo.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
public class ch2Predicate {

    private List<Apple> inventory = Arrays.asList(new Apple(150, Apple.Color.GREEN),
            new Apple(110, Apple.Color.GREEN),
            new Apple(170, Apple.Color.RED));

    @Test
    public void greenApple() {
        filterGreenApples(inventory).forEach(apple -> log.info("Apple: {}", apple.toString()));
    }

    // 사과 색이 그린이라면
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple:inventory) {
            if(Apple.Color.GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    @Test
    public void colorApple() {
        filterColorApples(inventory, Apple.Color.RED).forEach(apple -> log.info("apple: {}", apple.toString()));
    }

    // 사과 색이 조건과 같다면
    public static List<Apple> filterColorApples(List<Apple> inventory, Apple.Color color) {
        List<Apple> result = new ArrayList();
        for(Apple apple:inventory) {
            if(apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    @Test
    public void bigApple() {
        filterWeightApples(inventory, 150).forEach(apple -> log.info("apple: {}", apple));
    }

    // 사과 무개가 조건 보다 크면
    public static List<Apple> filterWeightApples(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList();
        for(Apple apple:inventory) {
            if(apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate a) {
        List<Apple> result = new ArrayList();
        for(Apple apple : inventory) {
            if(a.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 익명클래스
    List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
        @Override
        public boolean test(Apple apple) {
            return Apple.Color.RED.equals(apple.getColor());
        }
    });

    // 람다표현식
    List<Apple> result = filterApples(inventory, (Apple apple) -> Apple.Color.RED.equals(apple.getColor()));

    // 리스트 형식으로 추상화
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e:list) {
            if(p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    @Test
    public void threadRun() {
        // 그냥 스레드로 헬로우 월드 찍기
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Hello World");
            }
        });
        // 람다로 스레드 헬로우 월드 찍기
        Thread t = new Thread(() -> log.info("Hello World"));
    }

}
