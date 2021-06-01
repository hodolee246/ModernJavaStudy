package com.example.modernjava.first.ch21;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ch21 {

    @Test
    public void genericParam() {

        // 제네릭의 형식의 파라미터 생략가능
        // Map<String, List<String>> myMap = new HashMap<String, List<String>>();
        Map<String, List<String>> myMap = new HashMap<>();
    }

    @Test
    public void genericWildCard() {
        // 호환되지 않는 형식
        // List<Number> numbers = new ArrayList<Integer>();
        List<? extends Number> numbers = new ArrayList<Integer>();
    }
}


