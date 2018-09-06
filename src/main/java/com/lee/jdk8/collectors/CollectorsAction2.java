package com.lee.jdk8.collectors;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static com.lee.jdk8.collectors.CollectorsAction.menu;

public class CollectorsAction2 {
    public static void main(String[] args) {
        testGroupByConcurrentWithFunction();
        testGroupByConcurrentWithFunctionAndCollect();
        testGroupByConcurrentWithFunctionAndSupplierAndCollect();
        testJoining();
        testJoiningWithDelemiter();
        testJoiningWithDelemiterAndPrefixSuffix();
        testMapper();
        testMaxBy();
        testMinBy();

    }

    private static void testGroupByConcurrentWithFunction() {
        ConcurrentMap<Dish.Type, List<Dish>> map = menu.stream().collect(
                Collectors.groupingByConcurrent(Dish::getType));
        Optional.ofNullable(map.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(map).ifPresent(System.out::println);
    }

    private static void testGroupByConcurrentWithFunctionAndCollect() {
        System.out.println("testGroupByConcurrentWithFunctionAndCollect:");
        ConcurrentMap<Dish.Type, Double> map = menu.stream().collect(
                Collectors.groupingByConcurrent(Dish::getType, Collectors.averagingInt(Dish::getKaluli)));
        Optional.ofNullable(map).ifPresent(System.out::println);
    }

    private static void testGroupByConcurrentWithFunctionAndSupplierAndCollect() {
        System.out.println("-----------------");
        ConcurrentMap<Dish.Type, Double> map = menu.stream().collect(
                Collectors.groupingByConcurrent(
                        Dish::getType, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getKaluli)
                ));
        Optional.ofNullable(map.getClass()).ifPresent(System.out::println);
        Optional.ofNullable(map).ifPresent(System.out::println);
    }

    private static void testJoining() {
        System.out.println("testJoining...");
        Optional.of(
                menu.stream().map(Dish::getName).collect(Collectors.joining())
        ).ifPresent(System.out::println);

    }

    private static void testJoiningWithDelemiter() {
        System.out.println("testJoiningWithDelemiter...");
        Optional.of(
                menu.stream().map(Dish::getName).collect(Collectors.joining("-"))
        ).ifPresent(System.out::println);

    }

    private static void testJoiningWithDelemiterAndPrefixSuffix() {
        System.out.println("testJoiningWithDelemiterAndPrefixSuffix...");
        Optional.of(
                menu.stream().map(Dish::getName).collect(
                        Collectors.joining("-", "names[", "]")
                )
        ).ifPresent(System.out::println);

    }

    private static void testMapper() {
        System.out.println("testMapper...");
        Optional.of(
                menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining()))
        ).ifPresent(System.out::println);

    }

    private static void testMaxBy() {
        System.out.println("testMaxBy...");

        menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getKaluli)))
                .ifPresent(System.out::println);
    }

    private static void testMinBy() {
        System.out.println("testMinBy...");

        menu.stream().collect(Collectors.minBy(Comparator.comparing(Dish::getKaluli)))
                .ifPresent(System.out::println);
    }


}



