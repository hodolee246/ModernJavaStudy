package com.example.modernjava.first.ch3;

import com.example.modernjava.first.ch2.predicate.Predicate;
import com.example.modernjava.first.vo.Apple;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class lamdaExam {

    private List<Apple> inventory = Arrays.asList(new Apple(150, Apple.Color.GREEN),
            new Apple(110, Apple.Color.GREEN),
            new Apple(170, Apple.Color.RED));

    Runnable r = () -> log.info("Runnable run");
    Predicate<Apple> appleComparator = (a1) -> a1.getWeight() > 150;

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e:list) {
            if(p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
    // filter 를 이용하여 초록색 사과만 찾기
    List<Apple> greenApples = filter(inventory, (Apple a) -> "GREEN".equals(a.getColor()));
    // 무개가 150 이상인 사과만 찾기
    List<Apple> apples = filter(inventory, (Apple a) -> a.getWeight() > 150);
    
    /*
    boolean 표현식 (List<String> list) -> list.isEmpty()
    객체 생성       () -> new Apple(50);
    객체에서 소비 (Apple a) -> {
                      System.out.println(a.getWeight());
                  }
     */
}
