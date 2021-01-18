package com.wyj.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用semaphore信号量模拟抢车位
 *
 * semaphore 使用AQS实现
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        //三个车位
        Semaphore sm = new Semaphore(3);
        //模拟十个人
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {

                try {
                    //停入车位
                    sm.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到车位 ");
                    TimeUnit.MILLISECONDS.sleep(500);
                    sm.release();
                    //释放车位
                    System.out.println(Thread.currentThread().getName() + " 离开车位 ");
                } catch (InterruptedException e) {

                }
            }).start();
        }
    }


}
