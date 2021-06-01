# 람다 표현식
- - -
자바 8의 새로운 기능인 람다와 메소드 참조를 알아보자.

## 람다란 무엇인가
람다 표현식은 메소드로 전달할 수 있는 익명 함수를 단순화 한 것이라 할 수 있으며, 이름은 없지만 파라미터 리스트, 바디, 반환형식, 발생할 수 있는 예외 리스트는 가질 수 있다.

- 익명
    - 보통 메서드와 달리 이름이 없다.
- 함수
    - 특정 클래스에 종속되지 않기에 함수라 부른다.
- 전달
    - 메서드 인수로 전달하거나 변수로 저장할 수 있다.
- 간결성
    - 간결한 코드
    
~~~
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
~~~

람다 표현식의 구성
- 파라미터 리스트
    - Comparator의 compare 메소드 파라미터(사과 2개)
- 화살표
    - 화살표는 람다의 파라미터 리스트와 바디를 구분한다.
- 람다 바디
    - 두 사과의 무게를 비겨하며, 반환값에 해당하는 표현식이다.

## 람다가 어디에 어떻게 사용될까
주로 함수형 인터페이스라는 문맥에서 사용할 수 있다.

### 함수형 인터페이스
- 함수형 인터페이스는 단 하나의 추상 메서드가 있을경우 함수형 인터페이스이다.
- @FunctionalInterface 함수형임을 가리키는 어노테이션이다. 만약 함수형 인터페이스 조건을 충족하지 못한다면 컴파일 에러를 일으킨다.
    - 람다 표현식으로 함수형 인터페이스의 추상 메소드 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다.

### 함수 디스크립터
함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다. 람다 표현식의 시그니처를 서술하는 메서드를 **함수 디스크립터**라고 부른다.

## 람다 활용 : 실행 어라운드 패턴
설정과 정리 과정은 자원 처리에 사용되는 순환 패턴과 비슷하다. 즉 실제 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싸는 형태를 갖으며, 이를 실행 어라운드 패턴이라고 부른다.
    - 자연열기 - 처리 - 자원닫기
~~~
public String processFile() throws IOException {
    try (BufferedReader br = new bufferedReader(new FileReader("data.txt"))) {
        return br.readLine();
    }
}
~~~
이 코드는 한 번에 한줄만 읽을 수 있는 코드이다. 한 번에 여러줄을 읽고 싶다면 processFIle()을 전 챕터에서 사용한 동작파라미터를 이용하면 된다.  
~~~
// 람다로 변경한다면
String result = processFile(BufferedReader br) -> br.readLine() + br.readLine();
~~~

### 함수형 인터페이스를 이용해서 동작 전달
함수형 인터페이스 자리에 람다를 사용할 수 있다. 따라서 BufferedReader -> String과 IOException을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스를 만들어야 한다.

## 함수형 인터페이스 사용
다양한 람다 표현식을 사용하려면 공통의 함수 디스크립터를 기술하는 함수형 인터페이스 집합이 필요하다.

### Predicate, Consumer, Function
1. Predicate는 저번 챕태에서 정의해서 사용하였지만 사실 boolean을 반환하는 test() 메소드가 있는 함수형 인터페이스로 존재한다. 
2. Consumer역시 제네릭 형식 T 객체를 받아 void를 반환하는 accept() 메소드를 가지고 있다.

~~~
public <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for(T e: list) {
        if(p.test(e)) {
            result.add(e);
        }
    }
    return result;
}

@Test
void Predicate_사용() {
    Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
    List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);

    for(String s : nonEmpty) {
        System.out.println(s);
    }
}

public <T> void forEach(List<T> list, Consumer<T> c) {
    for(T t : list) {
        c.accept(t);
    }
}

@Test
void Consumer_사용() {
    forEach(numbers, (Integer i) -> System.out.println(i));
}

public <T, R> List<R> map(List<T> list, Function<T, R> f) {
    List<R> result = new ArrayList<>();
    for(T t : list) {
        result.add(f.apply(t));
    }
    return result;
}

@Test
void Function_사용() {
    List<Integer> l = map(Arrays.asList("lambdas", "in", "action"),
            (String s) -> s.length());
    for(Integer i : l) {
        System.out.println(i);
    }
}
~~~

1. 이렇게 제네릭 함수형 인터페이스가 아닌 특화된 형식의 함수형 인터페이스도 있다.
2. 자바의 모든 형식은 참조형 아니면 기본형에 해당한다.
    - 하지만 제네릭 파라미터에는 참조형만 사용할 수 있다.
    - 제네릭의 내부 구현 때문에 어쩔 수 없는 일이다.
3. 자바에서는 기본형을 참조형으로 변환시켜주는 박싱과 참조형을 기본형으로 변환시켜주는 언박싱을 지원한다.
    - 프로그래머의 편의성으로 자동으로 박싱 언박싱이 이루어지는 오토박싱 기능도 있다.
    - 단 박싱 언박싱에는 모두 변환 과정에서 비용이 발생한다.
  
## 형식 검사, 형식 추론, 제약

### 형식 검사

람다가 사용되는 컨텍스트를 이용해서 람다의 형식을 추론할 수 있다. 어떤 컨텍스트에서 기대되는 람다 표현식의 형식을 대상 형식이라고 한다.
~~~
List<Apple> heavierThan150g = filter(inventory, (Apple a) -> a.getWeight() > 150);

1. filter 메서드의 선언을 확인
2. filter 메서드는 두 번째 파라미터로 Predicate<Apple> 형식(대상 형식)을 기대한다.
3. Predicate<Apple>은 test라는 한 개의 추상 메서드를 정의하는 함수형 인터페이스다.
4. test 메서드는 Apple을 받아 boolean을 반환하는 함수 디스크립터를 묘사한다.
5. filter 메서드로 전달된 인수는 이와 같은 요구사항을 만족해야한다.
~~~

### 같은 람다, 다른 함수형 인터페이스
대상 형식이라는 특징 때문에 같은 람다 표현식이더라도 호환되는 추상 메서드를 가진 다른 함수형 인터페이스로 사용될 수 있다.

### 형식 추론
자바 컴파일러는 람다 표현식이 사용된 대상 형식을 이용해서 람다 표현식과 관련된 함수형 인터페이스를 추론한다. 즉, 대상 형식을 이용해서 함수 디스크립터를 알 수 있으므로 컴파일러는 람다의 시그니처도 추론할 수 있다.

~~~
Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
Comparator<Apple> c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight()); // 형식을 추론
~~~
### 지역 변수 사용
람다 표현식에서는 익명 함수가 하는 것처럼 자유 변수를 활용할 수 있으며, 이를 람다 캡처링이라고 부른다.
  - 단 해당 변수는 final로 선언되거나 final처럼 사용이 되어야 한다.
    - 힙에 저장되는 인스턴스 변수와 다르게 지역 변수는 스택에 저장된다.
    - 람다에서 지역 변수에 바로 접근할 수 있다는 가정하에 람다가 스레드에서 실행될 경우 변수를 할당한 슬드가 사라져서 변수 할당이 해제되었음에도 람다를 실행하는 스레드에서는 해당 변수에 접근하려 할 수 있다.
    - 따라서 자바 구현에서는 원래 변수에 접근을 혀용하는 것이 아닌 자유 지역 변수의 복사본을 제공한다.
    - 따라서 복사본의 값이 바뀌지 않아야 하므로 지역 변수에는 한 번만 값을 할당해야 한다는 규약이 생긴거다.
  
## 메서드 참조

1. 메서드 참조를 이용 시 기존 메서드 정의를 활용하여 람다처럼 전달할 수 있다.
    - 가독성을 높일 수 있다.
2. 만드는 방법
    - 정적 메소드 참조
        - Integer :: parseInt
    - 다양한 형식의 인스턴스 메소드 참조
        - String의 length 메소드는 String :: length로 사용이 가능하다.
    - 기존 객체의 인스턴스 메소드 참조
        - Transaction 객체를 할당받은 expensiveTransaction 지역 변수가 있고, Transaction 객체에 getValue가 있다면 expensiveTransaction :: getValue라고 표현할 수 있다.

### 생성자 참조
클래스명과 new를 이용하여 기존 생성자의 참조를 만들 수 있다.

## 람다, 메서드 참조 활용하기

### 코드 전달
1. sort 메서드에 정렬 전략을 어떻게 전달할 수 있을까? sort()는 다음과 같은 메소드를 가진다.
2. void sort(Comparator<? super E> c) 이 코드는 Comparator객체를 인수로 받아 두 사과를 비교한다. 객체 안에 동작을 포함시키는 방식으로 다양한 전략을 전달할 수 있다.
3. 이제 sort의 동작은 파라미터화 되었다고 말할 수 있다.

### 익명 클래스 사용
한번만 사용할 경우 코드 전달처럼 Comparator을 구현하기 보다는 익명 클래스를 사용한는게 더 좋다.

### 람다 표현식 사용
람다 표현식을 이용해 경량화된 문법을 이용해서 코드를 전달할 수 있으며, 함수형 인터페이스를 기대하는 곳 어디에서나 람다 표현식을 사용할 수 있다.

### 메서드 참조 사용
메서드 참조를 이용해 좀더 간소한 코드를 생성할 수 있다.

## 람다 표현식을 조합할 수 있는 유용한 메서드
디폴트 메소드 이용
### Comparator 조합
역정렬
  - 기존 Comparator 정렬에서 역순으로 정렬을 하고 싶은경우
  - 추가로 비교를 해서 정렬을 하고 싶은경우
~~~
inventory.sort(comparing(Apple:getWeight)
          .reversed() // 역정렬
          .thenComparing(Apple:getContry) // 무게가 동일할 경우 나라로 비교해서 정렬
~~~
### Predicate 조합
Predicate는 복잡한 프리디케이트를 만들 수 있도록 negate, and, or 3가지 디폴트 메소드를 지원한다.
1. negate
  - 빨간색이 아닌 사과처럼 반전을 시킨다.
2. and
  - and 조건을 추가할 수 있다.
3. or
  - or 조건을 추가할 수 있다.

### Function 조합
Function 또한 andThen, compose 2가지의 디폴트 메소드를 지원한다.
1. andThen
  - 주어진 함수를 먼저 적용한 결과를 다른 함수의 입력으로 전달하는 함수를 반환한다.
2. compose
  - 인수로 주어진 함수를 먼저 실행한 다음에 그 결과를 외부 함수의 인수로 제공한다.

## 정리
- - -
1. 람다 표현식은 익명함수 일종이며, 파라미터 리스트, 바디, 반환 형식을 가지며 예외를 던질 수 있다.
2. 람다 표현식으로 간결한 코드 구현할 수 있다.
3. 함수형 인터페이스는 하나의 추상메소드만 있으며, 이를 기대하는 곳에서만 람다 표현식을 쓸 수 있다.
4. 람다 표현식을 이용해 함수형 인터페이스의 추상 메서드를 즉석으로 제공할 수 있으며, 람다 표현식 전체가 함수형 인터페이스의 인스턴스로 취급한다.
5. 람다 표현식의 기대 형식을 대상 형식이라 한다.
6. 메서드 참조를 이용하면 기존의 메서드 구현을 재사용하고 직접 전달할 수 있다.






















