package com.example.modernjava.second.lambda;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class lambdaTest {

    List<String> listOfStrings = Arrays.asList("1", "2", "3", "4", "5", "6");
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    String numFilePath = getClass().getResource("/data.txt").getPath();

    public String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(numFilePath))) {
            return processor.process(br);
        }
    }

    @Test
    void 람다_전달() throws IOException {
        String oneLine = processFile((BufferedReader::readLine));
        String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());

        System.out.println("oneLine : " + oneLine);
        System.out.println("twoLines : " + twoLines);
    }

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
            System.out.println("s : " +s);
        }
    }

    public <T> void forEach(List<T> list, Consumer<T> c) {
        for(T t : list) {
            c.accept(t);
        }
    }

    @Test
    void Consumer_사용() {
        forEach(numbers, (Integer i) -> System.out.println("i : " +i));
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
            System.out.println("i : " +i);
        }
    }
}
