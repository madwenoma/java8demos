package com.lee.jdk8.others;

public class DefaultDemos {

    private void confuse(Object o) {
        System.out.println("object");
    }

    private void confuse(int[] arr) {
        System.out.println("int[]");
    }

    public static void main(String[] args) {
        A a = () -> 10;
        System.out.println(a.size());
        System.out.println(a.isEmpty());

        DefaultDemos d = new DefaultDemos();
        d.confuse(null);
        int[] arr = null;
        Object o = arr;
        d.confuse(o);
    }


    private interface A {
        int size();

        default boolean isEmpty() {
            return size() == 0;
        }
    }
}
