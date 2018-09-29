package com.lee.jdk8.forkjoin;

import java.util.Comparator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SpliteratorInAction {

    static String str = "all the moment has gone,like the tear int the rain" + "\n"
            + "nobody will pay attention to your past" + "\n"
            + "don't put your hope on the others";

    public static void main(String[] args) {
        MySpliteratorText mySpliteratorText = new MySpliteratorText(str);
        Optional.ofNullable(mySpliteratorText.stream().count()).ifPresent(System.out::println);

        mySpliteratorText.stream().forEach(System.out::println);
        mySpliteratorText.paralleStream().forEach(System.out::println);
    }

    static class MySpliteratorText {
        private final String[] data;


        MySpliteratorText(String text) {
            this.data = text.split("\n");
        }

        public Stream<String> stream() {
            return StreamSupport.stream(new MySpliterator(), false);
        }

        public Stream<String> paralleStream() {
            return StreamSupport.stream(new MySpliterator(), false);
        }

        private class MySpliterator implements Spliterator<String> {
            private int start, end;

            public MySpliterator() {
                this.start = 0;
                this.end = MySpliteratorText.this.data.length - 1;
            }

            public MySpliterator(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            public boolean tryAdvance(Consumer<? super String> action) {
                if (start <= end) {
                    action.accept(MySpliteratorText.this.data[start++]);
                    return true;

                }
                return false;
            }


            @Override
            public Spliterator<String> trySplit() {
                int mid = (end - start) / 2;
                if (mid <= 1) {
                    return null;
                }
                int left = start;
                int right = start + mid;
                start = start + mid + 1;
                return new MySpliterator(left, right);
            }

            @Override
            public long estimateSize() {
                return end - start;
            }

            @Override
            public long getExactSizeIfKnown() {
                return estimateSize();
            }

            @Override
            public int characteristics() {
                return IMMUTABLE | SIZED | SUBSIZED;
            }

            @Override
            public boolean hasCharacteristics(int characteristics) {
                return false;
            }

            @Override
            public Comparator<? super String> getComparator() {
                return null;
            }
        }
    }

}
