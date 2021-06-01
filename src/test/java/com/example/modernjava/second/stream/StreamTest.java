package com.example.modernjava.second.stream;

import com.example.modernjava.second.domain.Dish;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamTest {

    private List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 200, Dish.Type.MEAT),
            new Dish("salmon", false, 650, Dish.Type.FISH),
            new Dish("prawns", false, 100, Dish.Type.FISH),
            new Dish("rice", true, 120, Dish.Type.OTHER),
            new Dish("apple", true, 900, Dish.Type.OTHER)
    );

    @Test
    void 스트림_테스트() {
        List<String> names = menu.stream()
                                .filter(d -> {
                                   System.out.println("filtering : " + d.getName());
                                   return d.getCalories() > 300;
                                })
                                .map(d -> {
                                    System.out.println("mapping : " + d.getName());
                                    return d.getName();
                                })
                                .limit(3)
                                .collect(Collectors.toList());
        System.out.println("names : " + names);

        assertEquals(names.size(), 3);
    }

    @Test
    void 쇼트서킷() {
        List<Integer> cals = menu.stream().filter(d -> {
            System.out.println(d.isVegetarian());
            return d.isVegetarian() == false;
        }).map(d -> {
            System.out.println(d.getCalories());
            return d.getCalories();
        })
        .limit(2)
        .collect(Collectors.toList());
        System.out.println(cals);

        assertEquals(cals.size(), 2);
    }

    @Test
    void 인트_스트림사용() {
        int cal = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(cal);

        OptionalInt maxCal = menu.stream().mapToInt(Dish::getCalories).max();
        System.out.println(maxCal.orElse(0));

        assertEquals(cal, 2770);
    }

    List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER)
    );

    @Test
    void 슬라이싱_테스트1() {
        List<Dish> filteredMenu = specialMenu.stream()
            .filter(d -> d.getCalories() < 320)
            .collect(Collectors.toList());
        filteredMenu.forEach(d -> System.out.println(d.toString()));
    }

    @Test
    void 슬라이싱_테스트2() {
        List<Dish> sliceMenu1 = specialMenu.stream()
            .takeWhile(d -> d.getCalories() < 320)
            .collect(Collectors.toList());
        sliceMenu1.forEach(d -> System.out.println(d.toString()));
    }

    @Test
    void 슬라이싱_테스트3() {
        List<Dish> slicedMenu2 = specialMenu.stream()
            .dropWhile(d -> d.getCalories() < 320)
            .collect(Collectors.toList());
        slicedMenu2.forEach(d -> System.out.println(d.toString()));
    }

    List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

    @Test
    void 매핑_테스트() {
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());

        List<String[]> distinctWordLengthsArray = words.stream()
                .map(w -> w.split(""))  // string 이 아닌 string[] 배열로 바뀐다.
                .distinct()
                .collect(Collectors.toList());

        List<String> distinctWordLengthsArrayList = words.stream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)    // Stream<string[]>을 다시 stream<String> 으로 변경
                .collect(Collectors.toList());
    }

    @Test
    void 문자열_및_배열로_스트림_만들기() {
        // String 값
        Stream<String> stream = Stream.of("Morden", "Java", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);
        // 배열
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();   // 41
        System.out.println("sum : " + sum);
    }

    @Test
    void Null이될수있는_객체로_스트림_만들기() {
        Stream<String> homeValueStream = Stream.ofNullable(System.getProperty("home"));
        // flatMap 사용
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
    }

    @Test
    void 무한_스트림_만들기() {
        // iterate 사용만 다룸
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }
    /*
        실전연습 p 177
        1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리
        2. 거래자가 근무하는 모든 도시를 중복 없이 나열
        3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순 정렬
        4. 모든 거래자의 이름을 알파벳순으로 정렬
        5. 밀라노에 거래자가 있는지?
        6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값 출력
        7. 전체 트랜잭션 중 최대값은 얼마인지
        8. 전체 트랜잭션 중 최소값은 얼마인지.
     */

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    @Test
    void 문제1번() {
        List<Transaction> list = transactions.stream().filter(t ->
            t.getYear() == 2011
        )
        .sorted(Comparator
        .comparing(Transaction::getValue))
        .collect(Collectors.toList());
    }

    @Test
    void 문제2번() {
        List<String> list = transactions.stream().map(t -> t.getTrader().getCity())
        .distinct()
        .collect(Collectors.toList());

        System.out.println(list);
    }

    @Test
    void 문제3번() {
        transactions.stream().map(Transaction::getTrader).filter(trader ->
            trader.getCity().equals("Cambridge")
        ).sorted(Comparator.comparing(Trader::getName));

        System.out.println(transactions);
    }

    @Test
    void 문제4번() {
        transactions.stream().map(t -> t.getTrader().getName()).sorted().reduce("", (n1, n2) -> n1 + n2);
    }

    @Test
    void 문제5번() {
        boolean b = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));
    }

    @Test
    void 문제6번() {
        transactions.stream().filter(t ->
            "Cambridge".equals(t.getTrader().getCity())
        )
        .map(Transaction::getValue)
        .forEach(System.out::println);
    }

    @Test
    void 문제7번() {
        Optional<Transaction> value = transactions.stream().max(Comparator.comparing(Transaction::getValue));
    }

    @Test
    void 문제8번() {
        Optional<Transaction> value = transactions.stream().min(Comparator.comparing(Transaction::getValue));
    }

    public class Trader {
        private final String name;
        private final String city;

        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    public class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }
}