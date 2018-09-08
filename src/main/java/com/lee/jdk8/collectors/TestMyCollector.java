package com.lee.jdk8.collectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TestMyCollector<T> implements Collector<T, List<T>, List<T>> {

    public static void main(String[] args) {
        Collector<String, List<String>, List<String>> myCollector = new TestMyCollector<>();
        String[] arr = new String[]{"billy", "lee", "brown", "jenny", "tom"};
        List<String> list = Arrays.stream(arr).filter(s -> s.length() > 3).collect(myCollector);
        System.out.println(list);
        System.out.println("------------------------------------");
        //并行stream
        List<String> strList = Arrays.asList("billy", "lee", "brown", "jenny", "tom").parallelStream()
                .filter(s -> s.length() > 3).collect(myCollector);
        System.out.println(strList);
    }

    private void log(String log) {
        System.out.println(Thread.currentThread().getName() + "-" + log);
    }

    @Override
    public Supplier<List<T>> supplier() {
        log("supplier");
        return ArrayList::new;//必须是可变
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        log("accumulator");
        return List::add;
    }

    //fork join进行合并
    @Override
    public BinaryOperator<List<T>> combiner() {
        log("combiner");
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    //不做操作返回
    @Override
    public Function<List<T>, List<T>> finisher() {
        log("finisher");
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        log("characteristics");
        return Collections.unmodifiableSet(
                EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
}
