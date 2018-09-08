package com.lee.jdk8.collectors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.lee.jdk8.collectors.CollectorsAction.menu;

public class CollectorsAction4 {

    public static void main(String[] args) {
        testSummingDouble();
        testToCollectors();
        testToConcurrentMap();
        testToConcurrentMapWithBinaryOperator();
        testToList();
        testToSet();
        testToMap();
    }


    private static void testSummingDouble() {
        Double collect = menu.stream().collect(Collectors.summingDouble(Dish::getKaluli));
        Optional.of(collect).ifPresent(System.out::println);

        int sum = menu.stream().map(Dish::getKaluli).mapToInt(Integer::intValue).sum();
        System.out.println(sum);
    }


    //转为其他集合类型
    private static void testToCollectors() {
        System.out.println("testToCollectors...");
        Optional.of(menu.stream().collect(Collectors.toCollection(LinkedList::new))).
                ifPresent(System.out::println);
        Optional.of(
                menu.stream()
                        .filter(dish -> dish.getKaluli() > 600)
                        .collect(Collectors.toCollection(LinkedList::new))
        ).ifPresent(System.out::println);
    }

    /**
     * 注意：所有的toConcurrentMap都可以写成toMap，只是转换后的Map类型不同
     */
    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap...");
        Optional.of(
                menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getName, Dish::getKaluli))
        ).ifPresent(m -> {
            System.out.println(m);
            System.out.println(m.getClass());
        });
    }

    /**
     * Type:Total
     * 第三个参数
     */
    private static void testToConcurrentMapWithBinaryOperator() {
        System.out.println("testToConcurrentMapWithBinaryOperator...");
        Optional.of(
                menu.stream()
//                        .collect(Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b))//也可以指定生成的map类型如下面的写法
                        .collect(Collectors.toConcurrentMap(
                                Dish::getType, v -> 1L, (a, b) -> a + b, ConcurrentSkipListMap::new))//Supplier
        ).ifPresent(m -> {
            System.out.println(m);
            System.out.println(m.getClass());
        });
    }

    private static void testToList() {
        System.out.println("testToList...");
        Optional.of(menu.stream().collect(Collectors.toList()))
                .ifPresent(m -> {
                    System.out.println(m);
                    System.out.println(m.getClass());
                });
    }

    private static void testToSet() {
        System.out.println("testToSet...");
        Optional.of(menu.stream().collect(Collectors.toSet()))
                .ifPresent(m -> {
                    System.out.println(m);
                    System.out.println(m.getClass());
                });
    }

    /**
     * 转换map
     * 转换类型安全的map  collectingAndThen的用法
     */
    private static void testToMap() {
        System.out.println("testToMap...");
        Optional.of(
                menu.stream()
                        //.collect(Collectors.toMap(Dish::getName, Dish::getKaluli)) //转换map
                        .collect(Collectors.collectingAndThen(Collectors.toMap(Dish::getName, Dish::getKaluli), Collections::synchronizedMap))
        ).ifPresent(m -> {
            System.out.println(m);
            System.out.println(m.getClass());//Collections$SynchronizedMap
        });
    }

}


