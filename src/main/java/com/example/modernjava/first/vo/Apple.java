package com.example.modernjava.first.vo;


import java.util.Comparator;

public class Apple implements Comparator<Apple> {

    private int weight;
    private Color color;

    @Override
    public int compare(Apple o1, Apple o2) {
        return 0;
    }

    public enum Color {
        RED, GREEN
    }

    public Apple(int weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color=" + color +
                '}';
    }
}
