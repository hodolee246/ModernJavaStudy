# 동작 파라미터화 코드 전달하기

- - -
동작 파라미터란 아직은 어떻게 실행할 것인지 결정되지 않은 코드 블럭을 의미한다.
이 동작 파라미터를 적용한다면 매번 변경되는 요구사항을 효과적으로 변경할 수 있다.

## 변화하는 요구사하에 대응하기

### 녹색 사과 필터링

~~~
public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple a : inventory) {
        if("Green".equals(a.getColor()) {
            reuslt.add(a);
        }
    }
    return result;
}
~~~

해당 코드는 녹색 사과만 얻는 필터링 메소드이다. 만약 녹색 사과가 아닌 빨간 사과를 얻고 싶을 경우 새로운 조건을 가진 메소드를 새롭게 구현해야한다.
    - 이를 모드 가능캐한 메소드를 제작하더라도 색이 아닌 무게로 필터링을 하고싶은 새로운 요구사항이 생길경우 마찬가지 새로운 메소드를 제작해야한다.
    - 결국 중복코드가 무수히 발생하게 된다.

## 동작 파라미터화

위에서 말한 요구사항을 좀 더 유연하게 대응할 수 있도록 참, 거짓을 반환하는 선택 조건을 결정하는 인터페이스 프리디케이트를 정의해보자.

~~~
public interface ApplePredicate {
    boolean test (Apple apple);
}

// 녹색사과 찾기
public class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return "GREEN".equals(apple.getColor());
    }
}

// 무거운 사과찾기
public class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>();
    for (Apple a : inventory) {
        if(p.test(a)) {
            result.add(a);
        }
    }
    return result;
}
~~~

이렇게 구성할 경우 전략 디자인 패턴이자 동작 파라미터화가 준비가 끝이다.    
- 메서드가 다양한 전략을 받아 내부적으로 다양한 동작을 수행할 수 있다.    
    - 전략은 프리디케이트 인터페이스를 구현한 클래스가 전략이 된다.    
- 이 자체로 컬렉션을 반복하는 로직과 각 요소마다 적용할 동작을 분리할 수 있어 소프트웨어 엔지니어링적으로 큰 이득이다.    

## 복잡한 과정 간소화
위에서 진행한 전략 디자인 패턴으로 분리하더라도 매번 새로운 전략이 생길경우 새로운 클래스가 증가하게 된다. 이를 해결하기 위해 익명 클래스를 사용한다.
~~~
List<Apple> greenApples = filterApples(inventory, new ApplePredicate() {
    @Override
    public boolean test(Apple apple) {
        return "GREEN".equals(apple.getColor());
    }
});
~~~
**이마저도 반복되는 지저분한 코드가 생기게 되며, 이를 해결하기 위하여 람다로 재구현할 수 있다.**
~~~
List<Apple> greenApples = filterApples(inventory, (Apple a) -> "GREEN".equals(a.getColor()));
~~~

## 실전 예제
모든 예제를 포함한 코드
~~~
package com.example.modernjavainaction.predicate;

import com.example.modernjavainaction.domain.Apple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
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
            System.out.println(a.toString());
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
            System.out.println(a.toString());
        }
    }

    @Test
    void 람다_사용() {
        List<Apple> greenApples =
                filterApples(inventory, (Apple a) -> "GREEN".equals(a.getColor()));
        greenApples.forEach(a -> System.out.println(a.toString()));
    }

    @Test
    void 리스트_형식_추상화() {
        List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
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

        System.out.println(threadName.get());
    }
}
~~~

## 정리
- - -
1. 동작 파라미터화에서는 메서드 내부적으로 다양한 동작을 수행할 수 있도록 코드를 메서드 인수로 전달한다.
2. 동작 파라미터화는 람다를 이용하면 간단하게 할 수 있다.