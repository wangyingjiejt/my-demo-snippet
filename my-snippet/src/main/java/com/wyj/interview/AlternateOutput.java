package com.wyj.interview;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替输出
 * 线程1输出 1、2、3、4、5、
 * 线程2输出 a、b、c、d、e、
 * 最终结果1a2b3c4d...
 */
public class AlternateOutput {
    private final String s1 = "123456";
    private final String s2 = "abcdef";

    static Thread t1, t2;

    static final AtomicInteger atomicInteger = new AtomicInteger(1);

    final ReentrantLock lock = new ReentrantLock();

    Condition numCondition = lock.newCondition();

    Condition charCondition = lock.newCondition();


    /**
     * 使用LockSupport方法
     */
    @Test
    public void demo1() {

        t1 = new Thread(() -> {
            for (char c : s1.toCharArray()) {
                System.out.print(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (char c : s2.toCharArray()) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
        });
        t2.start();
        t1.start();
    }


    /**
     * wait\notify方法
     */
    @Test
    public void demo2() {

        t1 = new Thread(() -> {
            synchronized (this) {
                for (char c : s1.toCharArray()) {
                    try {
                        System.out.print(c);
                        notify();
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t2 = new Thread(() -> {
            synchronized (this) {
                for (char c : s2.toCharArray()) {
                    try {
                        wait();
                        System.out.print(c);
                        notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.start();
        t1.start();
    }


    /**
     * 使用两个condition实现
     *
     * @author W.Y.J
     * @Date 2021/1/6 11:04
     */
    @Test
    public void demo4() {
        t1 = new Thread(() -> {
            final Lock lc = lock;
            for (char c : s1.toCharArray()) {
                try {
                    lc.lock();
                    System.out.print(c);
                    charCondition.signal();
                    numCondition.await();
                } catch (InterruptedException e) {
                    lc.unlock();
                }

            }
        });

        t2 = new Thread(() -> {
            final Lock lc = lock;
            for (char c : s2.toCharArray()) {
                try {
                    lc.lock();
                    charCondition.await();
                    System.out.print(c);
                    numCondition.signal();
                } catch (Exception e) {
                    lc.unlock();
                }

            }
        });
        /**
         * 使用两个condition可以精确控制输出数据、输出字符两条生产线，
         * 即使每条生产线可以有多个线程同时工作，不影响两条线交替输出
         */
        Thread t3 = new Thread(() -> {
            final Lock lc = lock;
            for (char c : s2.toCharArray()) {
                try {
                    lc.lock();
                    charCondition.await();
                    System.out.print(c);
                    numCondition.signal();
                } catch (Exception e) {
                    lc.unlock();
                }

            }
        });

        t2.start();
        t1.start();
        t3.start();
    }


    /**
     * 利用自旋锁
     */
    @Test
    public void demo3() {

        t1 = new Thread(() -> {
            for (char c : s1.toCharArray()) {
                while (true) {
                    if (atomicInteger.get() == 1) {
                        System.out.print(c);
                        atomicInteger.set(2);
                        break;
                    }
                }
            }
        });

        t2 = new Thread(() -> {
            synchronized (this) {
                for (char c : s2.toCharArray()) {
                    while (true) {
                        if (atomicInteger.get() == 2) {
                            System.out.print(c);
                            atomicInteger.set(1);
                            break;
                        }
                    }
                }
            }
        });
        t2.start();
        t1.start();
    }


}
