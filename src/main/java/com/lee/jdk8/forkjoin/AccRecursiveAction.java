package com.lee.jdk8.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public class AccRecursiveAction extends RecursiveAction {


    private final int start;
    private final int end;
    private final int[] data;
    private final int LIMIT = 3;

    public AccRecursiveAction(int start, int end, int[] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected void compute() {

        if ((end - start) <= LIMIT) {
            for (int i = start; i < end; i++) {
                AccHelper.accumulate(data[i]);
            }
        } else {
            int mid = (start + end) / 2;
            AccRecursiveAction left = new AccRecursiveAction(start, mid, data);
            AccRecursiveAction right = new AccRecursiveAction(mid, end, data);
            left.fork();
            right.fork();
            left.join();
            right.join();
        }
    }

    static class AccHelper {
        final static AtomicInteger value = new AtomicInteger(0);

        static void accumulate(int data) {
            value.getAndAdd(data);
        }

        public static int getResult() {
            return value.get();
        }

        static void reset() {
            value.set(0);
        }
    }
}
