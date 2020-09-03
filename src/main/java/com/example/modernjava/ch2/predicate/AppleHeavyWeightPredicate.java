package com.example.modernjava.ch2.predicate;

import com.example.modernjava.vo.Apple;

public class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}
