package com.lee.jdk8.forkjoin;

import java.util.concurrent.RecursiveTask;

public class AccRecursiveTask extends RecursiveTask<Integer> {


    private final int start;
    private final int end;
    private final int[] data;
    private final int LIMIT = 3;

    public AccRecursiveTask(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected Integer compute() {

        if ((end - start) <= LIMIT) {
            int result = 0;
            for (int i = start; i < end; i++) {
                result += data[i];
            }
            return result;
        }

        int mid = (start + end) / 2;
        AccRecursiveTask left = new AccRecursiveTask(start, mid, data);
        AccRecursiveTask right = new AccRecursiveTask(mid, end, data);
        left.fork();
        Integer rightResult = right.compute();
        Integer leftResult = left.join();
        return leftResult + rightResult;
    }
}
