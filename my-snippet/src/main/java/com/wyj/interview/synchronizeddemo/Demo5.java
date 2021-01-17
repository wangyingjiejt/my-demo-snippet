package com.wyj.interview.synchronizeddemo;

import java.util.concurrent.TimeUnit;

/**
 * Phone的两个方法都被synchronized修饰，其中一个又被static修饰，执行顺序？
 * @author W.Y.J
 * @Date 2021/1/17 17:18
 */
public class Demo5 {

    public static void main(String[] args) {
        Phone5 phone1 = new Phone5();
        new Thread(() -> phone1.sendMsg(), "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> phone1.call(), "B").start();

    }

}

class Phone5 {
    public static synchronized void sendMsg() {

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
     * call other...
     * send msg...
     * sendMsg被static修饰，故被调用时锁住的时Class这个模板，而call()是被实例调用的，被锁住的是实例this
     * 也即两把锁，两个方法不存在竞争关系
     */




}