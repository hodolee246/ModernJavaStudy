package com.example.modernjava.second.predicate;

import com.example.modernjavainaction.domain.Apple;

public class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return "GREEN".equals(apple.getColor());
    }
}
