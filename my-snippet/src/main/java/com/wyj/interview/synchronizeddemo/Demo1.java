package com.wyj.interview.synchronizeddemo;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 *
 * Phone对象的两个方法都被synchronized修饰，执行的先后顺序？
 *
 * @author W.Y.J
 * @Date 2021/1/17 17:14
 */
public class Demo1 {

    /**
     * Note:
     * 使用单元测试会影响多线程的测试结果，main会等所有线程执行完才会销毁,
     * 单元测试则不会
     */

    @Test
    public void test() {

    }

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> phone.sendMsg(), "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> phone.call(), "B").start();

    }

}

class Phone {
    public synchronized void sendMsg() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send msg...");
    }

    public synchronized void call() {

        System.out.println("call other...");
    }


















    /*****************************************************解析**************************************************/


    /**
     * print result:
     * send msg...
     * call other...
     * 为什么再 sendMsg方法等待4s的情况下还是先输出send sms？
     * 注意到sendMsg和call都使用了synchronized关键字修饰，synchronized锁的是phone这个对象实例this,
     * 所以再先执行sendMsg方法时就获取到了锁，虽然在睡眠但是锁并没有释放，而call方法拿不到锁肯定时无法执行的
     * 只能等待锁释放后竞争到锁再执行。
     *
     *
     * 另外需要注意的是 线程调用sleep方法，不会释放锁么？
     * sleep方法只会让出cpu时间片，让其它线程有机会去获取cpu资源，但是并不会释放锁，sleep不影响锁机制，注意和wait的区别
     */




}