package com.lee.jdk8.thread;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {

    private static int i = 1;
    static CountDownLatch latch = new CountDownLatch(4);

    static class AutoIncre implements Runnable {
        Lock lock = new ReentrantLock();

        @Override
        public void run() {
            latch.countDown();
            lock.lock();
            System.out.println(Thread.currentThread().getId() + " " + "get lock");
            try {
                Thread.sleep(new Random().nextInt(3) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            debugLockInfo((ReentrantLock) lock);
            i++;
            lock.unlock();
            System.out.println(Thread.currentThread().getId() + " " + "release lock");

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        AutoIncre autoIncre = new AutoIncre();
        Thread t = new Thread(autoIncre);
        Thread t2 = new Thread(autoIncre);
        Thread t3 = new Thread(autoIncre);
        Thread t4 = new Thread(autoIncre);
        t.start();
        t2.start();
        t4.start();
        t3.start();
        latch.await();
        System.in.read();
    }

    private static void debugLockInfo(ReentrantLock lock) {
        long id = Thread.currentThread().getId();
        System.out.println(id + "-" + lock.getHoldCount());
        System.out.println(id + "-" + lock.getQueueLength());
    }
}
