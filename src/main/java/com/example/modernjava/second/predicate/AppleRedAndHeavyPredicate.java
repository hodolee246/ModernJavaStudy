package com.example.modernjava.second.predicate;

import com.example.modernjavainaction.domain.Apple;

public class AppleRedAndHeavyPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return "RED".equals(apple.getColor()) && apple.getWeight() > 150;
    }
}
