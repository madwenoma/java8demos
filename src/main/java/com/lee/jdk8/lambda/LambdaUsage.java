package com.lee.jdk8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LambdaUsage {

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple("red", 120), new Apple("green", 19));
        System.out.println(filter(list, apple -> (apple.getColor().equals("red") && apple.getWeight() == 120)));

        list = filterBi(list, (s, w) -> s.equals("green") && w.equals(19));
        System.out.println(list);


        System.out.println("============Supplier==========");

        Supplier<String> s = String::new;
        System.out.println(s.get().getClass());
        Apple a = createApple(() -> new Apple("yellow", 100));
        System.out.println(a);
    }



    private static Apple createApple(Supplier<Apple> supplier) {
        return supplier.get();
    }

    public static List<Apple> filter(List<Apple> source, Predicate<Apple> pre) {
        return source.stream().filter(pre).collect(Collectors.toList());
    }

    public static List<Apple> filterBi(List<Apple> source, BiPredicate<String, Integer> pre) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : source) {
            if (pre.test(apple.getColor(), apple.getWeight())) {
                list.add(apple);
            }
        }
        return list;
    }

}

class Apple {
    private String color;
    private int weight;

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Apple() {

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }
}
