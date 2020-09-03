package com.example.modernjava.ch2.predicate;

import java.util.ArrayList;
import java.util.List;

public interface Predicate<T> {
    boolean test(T t);
}
