package com.lee.jdk8.lambda;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法推导
 * cosumer使用，入参看起来是一个方法
 */
public class MethodReference {
    public static void main(String[] args) {
        System.out.println("============Consumer==========");
//        Consumer<String> consumer = (s) -> System.out.println(s);
        useConsumer(s -> System.out.println(s), "hello consumer");
        useConsumer(System.out::println, "tuidao");

        int i = Integer.parseInt("123");
        Function<String,Integer> f = Integer::parseInt;
        Integer num = f.apply("123");
        System.out.println(num);

        BiFunction<String,Integer,Character> bf = String::charAt;
        Character c = bf.apply("hello",1 );
        System.out.println(c);

        String str = "Hello";
        Function<Integer,Character> fun = str::charAt;
        System.out.println(fun.apply(0));

        BiFunction<String, Integer,Apple> appleFun = Apple::new;
        Apple a = appleFun.apply("brown",20);
        System.out.println(a);
    }

    private static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
        consumer.accept(t);
    }
}
