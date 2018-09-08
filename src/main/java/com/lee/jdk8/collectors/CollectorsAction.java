package com.lee.jdk8.collectors;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsAction {
    public static final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("fresh fries", false, 530, Dish.Type.OTHER),
            new Dish("rice", false, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.FRUIT),
            new Dish("pizza", false, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    public static void main(String[] args) {
        testAveragingDouble(menu);
        testCollectingAndThen();
        testCounting();
        testGroupingByFunction();
        testGroupingByFunctionAndCollector();
        testGroupingByFunctionAndSupplierAndCollector();
        testSummarizingInt();
    }

    private static void testAveragingDouble(List<Dish> menu) {
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getKaluli)))
                .ifPresent(System.out::println);
    }

    private static void testCollectingAndThen() {
        Optional.ofNullable(menu.stream().collect(Collectors.collectingAndThen(
                Collectors.averagingInt(Dish::getKaluli), d -> "this is average kaluli:" + d)))
                .ifPresent(System.out::println);

        try {

            //通过collectingAndThen限制list类型
            List<Dish> list = menu.stream().filter(dish -> dish.getType().equals(Dish.Type.MEAT)).collect(
                    Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)
            );
            Dish noodles = new Dish("noodles", false, 100, Dish.Type.OTHER);
            list.add(noodles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testCounting() {
        Optional.ofNullable(menu.stream().collect(Collectors.counting()))
                .ifPresent(System.out::println);
    }


    private static void testGroupingByFunction() {
        Optional.ofNullable(menu.stream().collect(Collectors.groupingBy(Dish::getType)))
                .ifPresent(System.out::println);
    }

    /**
     * result:
     * {FRUIT=1, MEAT=3, FISH=2, OTHER=3}
     * {FRUIT=120.0, MEAT=633.3333333333334, FISH=375.0, OTHER=476.6666666666667}
     */
    private static void testGroupingByFunctionAndCollector() {
        Optional.ofNullable(menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting())))
                .ifPresent(System.out::println);

        Optional.ofNullable(menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getKaluli))))
                .ifPresent(System.out::println);
    }


    /**
     * 限定返回map的类型为treeMap
     */
    private static void testGroupingByFunctionAndSupplierAndCollector() {
        Map<Dish.Type, Double> map = menu.stream().collect(
                Collectors.groupingBy(Dish::getType, Collectors.averagingInt(Dish::getKaluli)));
        System.out.println(map.getClass());//HashMap
        map = menu.stream().collect(
                Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.averagingInt(Dish::getKaluli)));
        System.out.println(map.getClass());//TreeMap
        Optional.of(map).ifPresent(System.out::println);
    }


    /**
     * 各种统计字段：
     * result:
     * IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
     */
    private static void testSummarizingInt() {
        IntSummaryStatistics statistics = menu.stream().collect(Collectors.summarizingInt(Dish::getKaluli));
        System.out.println(statistics);
    }

}



