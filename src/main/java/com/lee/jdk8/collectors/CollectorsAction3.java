package com.lee.jdk8.collectors;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static com.lee.jdk8.collectors.CollectorsAction.menu;

public class CollectorsAction3 {

    public static void main(String[] args) {
        testPartitionByWithPredicate();
        testPartitionByWithPredicateAndCollector();
        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentiy();
        testSummarizing();

    }


    private static void testPartitionByWithPredicate() {
        Map<Boolean, List<Dish>> collect = menu.stream().collect(Collectors.partitioningBy(Dish::isFruit));
        Optional.of(collect).ifPresent(System.out::println);
    }


    /**
     * 根据是否是Fruit类型分组,并计算平均卡路里
     */
    private static void testPartitionByWithPredicateAndCollector() {
        System.out.println("testPartitionByWithPredicateAndCollector...");
        Map<Boolean, Double> collect = menu.stream().collect(
                Collectors.partitioningBy(Dish::isFruit, Collectors.averagingInt(Dish::getKaluli))
        );
        Optional.of(collect).ifPresent(System.out::println);
    }

    //
    private static void testReducingBinaryOperator() {
        menu.stream().collect(Collectors.reducing(
                BinaryOperator.maxBy(
                        Comparator.comparingInt(Dish::getKaluli)
                )
        )).ifPresent(System.out::println);
    }

    //
    private static void testReducingBinaryOperatorAndIdentiy() {
        Integer collect = menu.stream().map(Dish::getKaluli).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));

        Integer collect2 = menu.stream().collect(Collectors.reducing(0, Dish::getKaluli, (d1, d2) -> d1 + d2));

        System.out.println(collect);
        System.out.println(collect2);
    }

    /**
     * 计算综合统计，包含总数、和、平均、最大、最小等
     */
    private static void testSummarizing() {
        Optional.of(
                menu.stream().collect(Collectors.summarizingInt(Dish::getKaluli))
        ).ifPresent(System.out::println);
        Optional.of(
                menu.stream().collect(Collectors.summarizingLong(Dish::getKaluli))
        ).ifPresent(System.out::println);
        Optional.of(
                menu.stream().collect(Collectors.summarizingDouble(Dish::getKaluli))
        ).ifPresent(System.out::println);
    }
//
//    private static void testPartitionByWithPredicate() {
//
//    }
//
//    private static void testPartitionByWithPredicate() {
//
//    }


}


