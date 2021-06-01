package com.example.modernjava.first.ch3;

import com.example.modernjava.first.ch2.predicate.ApplePredicate;
import com.example.modernjava.first.vo.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ch3Lamda {

    private List<Apple> inventory = Arrays.asList(new Apple(150, Apple.Color.GREEN),
            new Apple(110, Apple.Color.GREEN),
            new Apple(170, Apple.Color.RED));

    @Test
    public void filterApples() {
        List<Apple> result = filterApples(inventory, (apple) -> "RED".equals(apple.getColor()));
        System.out.println(result.size());
        result.forEach((apple) -> log.info(apple.toString()));
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

}
