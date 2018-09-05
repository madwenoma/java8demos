package com.lee.jdk8.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
 * System.out.println(“sum is:”+nums.stream().filter(num -> num != null).
 * distinct().mapToInt(num -> num * 2).
 * peek(System.out::println).skip(2).limit(4).sum());
 * 这段代码演示了上面介绍的所有转换方法（除了flatMap），
 * 简单解释一下这段代码的含义：给定一个Integer类型的List，
 * 获取其对应的Stream对象，然后进行过滤掉null，再去重，
 * 再每个元素乘以2，再每个元素被消费的时候打印自身，
 * 在跳过前两个元素，最后去前四个元素进行加和运算
 * <p>
 * 注意peek的用法是，消费的同时做什么事
 */
public class StreamDemos {
    public static void main(String[] args) {
        collectorDemo();
    }

    public static void collectorDemo() {
        Set<Integer> collectSet = Stream.of(1, 1, 3, 4)
                .collect(Collectors.toSet());
        collectSet.stream().forEach(System.out::println);
        String strJoin = Stream.of("1", "2", "3", "4")
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(strJoin);
        Stream.of(1, 1, 3, 3, 5, 4).distinct().forEach(System.out::println);
    }

    public static void createStream() {
        Stream.of(1, 2, 4);
        Stream.of("taobao", "hehe").forEach(System.out::println);
        Stream.of("tabao".split("b")).forEach(System.out::println);
    }

    public static void streamIterate() {
        Stream<Integer> stream = Stream.iterate(0, n -> n + 2).limit(20);
        stream.forEach(System.out::println);
    }

    public static void streamRandom() {
        int s = Stream.generate(() -> new Random().nextInt(5)).limit(1).collect(Collectors.toList()).get(0);
        System.out.println(s);
    }

    public static void filter() {
        List<Integer> nums = new ArrayList();
        nums.add(1);
        nums.add(null);
        nums.add(3);
        int size = (int) nums.stream().filter(num -> num != null).count();
        System.out.println(size);
    }

    /**
     * reduce 操作可以实现从Stream中生成一个值，其生成的值不是随意的，
     * 而是根据指定的计算模型。比如，之前提到count、min和max方
     * 法，因为常用而被纳入标准库中。事实上，这些方法都是reduce操作。
     */
    public static void reduce() {
        //reduce demo 1
        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : " + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println("accResult: " + accResult.get());
        System.out.println("--------");
        //demo2, 该用法不会返回optinal，因为不可能返回null
        //第一个参数是0，初始值
        int demo2Result = Stream.of(1, 2, 3, 4)
                .reduce(0, (acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    return acc;
                });
        System.out.println(demo2Result);
        //reduce demo3
        ArrayList<Integer> demo3Result = Stream.of(1, 2, 3, 4)
                .reduce(new ArrayList<>(),
                        (acc, item) -> {
                            acc.add(item);
                            System.out.println("item: " + item);
                            System.out.println("acc+ : " + acc);
                            System.out.println("BiFunction");
                            return acc;
                        }, new BinaryOperator<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> apply(ArrayList<Integer> acc, ArrayList<Integer> item) {
                                System.out.println("BinaryOperator");
                                acc.addAll(item);
                                System.out.println("item: " + item);
                                System.out.println("acc+ : " + acc);
                                System.out.println("--------");
                                return acc;
                            }
                        });
        System.out.println("accResult_: " + demo3Result);
    }
}
