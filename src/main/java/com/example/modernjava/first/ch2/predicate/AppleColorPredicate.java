package com.example.modernjava.first.ch2.predicate;

import com.example.modernjava.first.vo.Apple;

public class AppleColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return "GREEN".equals(apple.getColor());
    }
}
