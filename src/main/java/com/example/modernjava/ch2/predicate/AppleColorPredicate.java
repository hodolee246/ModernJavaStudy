package com.example.modernjava.ch2.predicate;

import com.example.modernjava.vo.Apple;

public class AppleColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return "GREEN".equals(apple.getColor());
    }
}
