package com.example.modernjava.second.predicate;

import com.example.modernjavainaction.domain.Apple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class PredicateTest {

    List<Apple> inventory = Arrays.asList(new Apple(Apple.Color.RED, 130)
                                        , new Apple(Apple.Color.GREEN, 150)
                                        , new Apple(Apple.Color.GREEN, 170));

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple a : inventory) {
            if(p.test(a)) {
                result.add(a);
            }
        }
        return result;
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e: list) {
            if(p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }


    @Test
    void 프리디케이트_사용() {
        List<Apple> greenApples = filterApples(inventory, new AppleGreenColorPredicate());
        for(Apple a : greenApples) {
            System.out.println("a : " +a.toString());
        }
    }

    @Test
    void 익명클래스_사용() {
        List<Apple> greenApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "GREEN".equals(apple.getColor());
            }
        });
        for(Apple a : greenApples) {
            System.out.println("a : " +a.toString());
        }
    }

    @Test
    void 람다_사용() {
        List<Apple> greenApples =
                filterApples(inventory, (Apple a) -> "GREEN".equals(a.getColor()));
        greenApples.forEach(a -> System.out.println("a : " +a.toString()));
    }

    @Test
    void 리스트_형식_추상화() {
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
        for(Integer i : evenNumbers) {
            System.out.println("i : " +i.toString());
        }
    }

    @Test
    void 정렬() {
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });
    }

    @Test
    void 정렬_람다() {
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
    }

    @Test
    void 정렬_메서드참조() {
        inventory.sort(Comparator.comparing(Apple::getWeight));
    }

    @Test
    void Runnable_코드_실행() {
        new Thread(() -> System.out.println("Hello Rnnable_코드_실행()"));
    }

    @Test
    void GUI_이벤트_처리하기() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> threadName = executorService.submit(() -> Thread.currentThread().getName());

        System.out.println("thread name : " +threadName.get());
    }
}
