# 스트림 활용
- - -

## 필터링

### 고유 요소 필터링
1. distinct
    - 중복제거 메소드

## 스트림 슬라이싱
filter, takeWhile, dropWhile 제공
### 프레디케이트를 이용한 슬라이싱

1. takeWhile
    - 무한 스트림을 포함한 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스할 수 있다.
2. dropWhile
    - takeWhile과 정반대로 dropWhile은 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다. 무한한 남은 요소를 가진 무한 스트림에서도 동작한다.

### 스트림 축소
1. limit
    - 요소 제한
2. skip
    - 요소 건너뛰기
    
## 매핑
특정 데이터를 선택하는 map, flatMap 제공
### 스트림의 각 요소에 함수 적용
스트림은 함수를 인수로 받는 map을 지원하며, 인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다. (변환에 가까운 매핑)

### 스트림의 평면화

~~~
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
~~~
1. map
   - 배열로 매핑
2. flatMap
   - 스트림으로 매핑

## 검색과 매칭
allMatch, anyMatch, noneMatch, findFirst, findAny등 다양한 유틸리티 제공

### 프레디케이트 요소 확인
1. 프레디케이트가 적어도 한 요소와 일치한지 확인
~~~
boolean isAnyVegetarian = menu.stream().anyMatch(Dish::isVegetarian);   
~~~
2. 프레디케이트가 모든 요소와 일치하는지 검사
~~~
boolean isAllLowCalorieThen1000 = menu.stream().allMatch(d -> d.getCalories() < 1000);
~~~
3. 프레디케이트가 모든 요소와 일치하지 않는지 검사
~~~
boolean isNoneLowCaloriesThen1000 = mewnu.stream().noneMatch(d -> d.getCalories() <= 1000);
~~~

### 요소검색
1. findAny
   - 현재 스트림에서 임의의 요소를 반환한다. (return optional)
2. findFirst
   - 일부 스트림에는 논리적인 아이템 순서가 정해져 있을 수 있으며, 순서가 있을경우 사용된다.
   - 현재 스트림에서 첫 번째 요소를 반환한다. (return optional)
   
## 리듀싱
스트림 요소를 조합하여 더욱 더 복잡한 질의를 수행할때 리듀싱 연산을 사용한다.
   - 함수형 프로그래밍에서는 **폴드** 라고 부른다.
~~~
int sum = numbers.stream().reduce(0, (a, b) -> a + b); // 초깃값 0, 두 요소를 조합하여 새로운 값을 제작하는 람다 (a,b) -> a + b
int product = numbers.stream().reduce(1, (a, b) -> a * b);
~~~
초기값이 없는 reduce
   - 스트림에 아무 요소도 없는 상황일 경우 초깃값도 없기에 값을 낼 수 없게된다.
   - 그렇기에 반환은 Optional을 감싸서 반환이 이루어진다.

### 최댓값과 최솟값
~~~
Optional<Integer> max = numbers.stream().reduce(Integer::max);
Optional<Integer> min = numbers.stream().reduce(Integer::min);
~~~

## 숫자형 스트림
~~~
int cal = menu.stream().map(Dish::getCalories).sum();
~~~
- 해당 코드에서 sum() 은 호출할 수 없다. map 메소드가 Stream<T>을 반환하기 떄문이다. 스트림의 요소 형식은 Integer지만 인터페이스에는 sum이 없다.
- menu처럼 Stream<Dish> 형식의 요소만 있는 경우 sum 연산을 수행할 수 없다.
   - 이러한 문제를 해결하기 위하여 스트림은 기본형 특화 스트림을 제공한다.
   
### 기본형 특화 스트림
#### 숫자 스트림 매핑
~~~
int cal = menu.stream()  // Stream<Dish> 반환
   .mapToInt(Dish::getCalories)  // IntStream 반환
   .sum();
~~~
mapToInt 메소드는 각 요리에서 모든 칼로리를 추출한 뒤 IntStream을 반환한다.
   - 따라서 IntStream 인터페이스에서 제공하는 sum 메소드를 사용하여 칼로리 합계를 구할 수 있다.

#### 객체 스트림으로 복원
숫자 스트림을 만든 다음 기존 특화되지 않은 스트림으로 복원
~~~
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);  // 스트림을 숫자 스트림으로 변환
Stream<Integer> stream = intStream.boxed();  // 숫자 스트림을 스트림으로 변환
~~~

#### 기본형 특화 Optional
마찬가지로 Optional 도 기본형 특화 스트림 버전도 제공한다. OptionalInt, OptionalDouble, OptionalLong
~~~
int max = maxCalories.orElse(1); // 값이 없을 때 기본 최댓값을 명시적으로 설정
~~~

### 숫자 범위
IntStream, LongStream에는 range, rangeClosed라는 정적 메서드를 제공한다. 두 메서드 모두 첫 번째 인수로 시작값, 두 번째 인수로 종료값을 갖는다.
   - range(1, 100) 을 이용 시 입력값 두개를 제외한 (2~99)가 제공된다.
   - rangeClosed(1, 100)을 이용 시 입력값 두개를 포함한 (1~100)이 제공된다.
~~~
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 ==0); // 1~100까지 짝수만 존재
~~~

## 스트림 만들기

### 값으로 스트림 만들기
~~~
// String 값
Stream<String> stream = Stream.of("Morden", "Java", "In", "Action");
stream.map(String::toUpperCase).forEach(System.out::println);
// 배열
int[] numbers = {2, 3, 5, 7, 11, 13};
int sum = Arrays.stream(numbers).sum();   // 41
~~~

### null이 될 수 있는 객체로 스트림 만들기
~~~
Stream<String> homeValueStream = Stream.ofNullable(System.getProperty("home"));
        // flatMap 사용
        Stream<String> values = Stream.of("config", "home", "user")
                .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
~~~

### 함수로 무한 스트림 만들기
iterate는 요청할 때마다 값을 생산할 수 있으며, 끝이 없는 무한스트림을 만든다. 이러한 스트림을 언바운드 스트림이라고 표현한다.
~~~
// iterate 사용만 다룸
Stream.iterate(0, n -> n + 2)
   .limit(10)
   .forEach(System.out::println);
~~~

## 정리
- - -
1. 스트림 API를 이용하면 복잡한 데이터 처리 질의를 표현할 수 있다.
2. 소스가 정렬된 경우 takeWhile, dropWhile을 사용하자.
3. map, flatMap 메서드로 스트림의 요소를 추출 및 변환할 수 있다.
4. findFirst, findAny 메서드 등 allMatch, noneMatch, anyMatch 메서드는 결과를 찾는 즉시 반환하는 쇼트서킷이며, 스트림을 전체 처리하지 않는다.
5. reduce를 사용 시 스트림의 모든 요소를 반복 조합하여 값을 도출할 수 있다.
6. filter, map 등은 상태를 저장하지 않는 연산이며, reduce 같은 연산은 값을 계산하는 데 필요한 상태를 저장한다.
