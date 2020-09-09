package com.example.modernjava.ch4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExam {

    public static void main(String[] args) {
        List<String> titleList = Arrays.asList("java", "title", "8", "action");
        Stream<String> streamExam = titleList.stream();
        // java, title, 8, action 출력
        streamExam.forEach(System.out::println);
        // IllegalStateException 발생
        streamExam.forEach(System.out::println);

        List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 200, Dish.Type.MEAT),
            new Dish("salmon", false, 650, Dish.Type.FISH),
            new Dish("prawns", false, 100, Dish.Type.FISH),
            new Dish("rice", true, 120, Dish.Type.OTHER),
            new Dish("apple", true, 900, Dish.Type.OTHER)
        );

        // 외부 반복 for-each 예제
        List<String> names = new ArrayList<>();
        for(Dish d : menu) {
            if(d.getCalories() > 500) {
                names.add(d.getName());
            }
        }
        // 외부 반복 iterator 예제
        List<String> names2 = new ArrayList<>();
        // 책은 제너릭이 String 인데... 에러 발생 -> Dish로 변경
        Iterator<Dish> iterator = menu.iterator();
        while(iterator.hasNext()) {
            Dish d = iterator.next();
            names2.add(d.getName());
        }
        // 내부 반복 예제
        List<String> names3 = menu.stream()
                                .map(Dish::getName) // map 메서드를 getName 메서드로 파라미터화해서 요리명을 추출
                                .collect(Collectors.toList());
        // 중간 연산 최종 연산 구별
        List<String> calExam = menu.stream()
                                .filter(dish -> dish.getCalories() > 150) // 중간 연산
                                .map(Dish::getName)                       // 중간 연산
                                .limit(2)                                 // 중간 연산
                                .collect(Collectors.toList());            // 최종 연신

        List<Integer> calories = menu.stream()
                                    .filter(Dish::isVegetarian)
                                    .map(Dish::getCalories)
                                    .limit(1)
                                    .collect(Collectors.toList());

    }
}