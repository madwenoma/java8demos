package com.lee.jdk8.forkjoin;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinTest {
    private static int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


    public static void main(String[] args) {
        AccRecursiveTask accRecursiveTask = new AccRecursiveTask(0, data.length, data);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(accRecursiveTask);
        System.out.println(result);
    }
}
