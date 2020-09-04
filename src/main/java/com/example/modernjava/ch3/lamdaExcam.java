package com.example.modernjava.ch3;

import com.example.modernjava.ch2.predicate.Predicate;
import com.example.modernjava.vo.Apple;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class lamdaExcam {

    Runnable r = () -> log.info("Runnable run");
    Predicate<Apple> appleComparator = (a1) -> a1.getWeight() > 150;

}
