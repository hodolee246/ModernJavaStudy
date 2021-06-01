package com.example.modernjava.second.predicate;

import com.example.modernjavainaction.domain.Apple;

public class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}
