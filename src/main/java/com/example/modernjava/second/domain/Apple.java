package com.example.modernjava.second.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Apple {
    private Color color;
    private Integer weight;

    public enum Color {
        GREEN, RED
    }
}
