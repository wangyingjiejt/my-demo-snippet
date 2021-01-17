package com.wyj.interview.synchronizeddemo;

import java.util.concurrent.TimeUnit;

/**
 * Phone的两个方法都被synchronized修饰，但是new两个对象分别调用两个方法，执行顺序？
 * @author W.Y.J
 * @Date 2021/1/17 17:18
 */
public class Demo3 {

    public static void main(String[] args) {
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();
        new Thread(() -> phone1.sendMsg(), "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> phone2.call(), "B").start();

    }

}

class Phone3 {
    public synchronized void sendMsg() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send msg...");
    }

    public void call() {

        System.out.println("call other...");
    }














    /*****************************************************解析**************************************************/


    /**
     * print result:
     * call other...
     * send msg...
     *
     * 结果看出先输出call other...,为什么？虽然两个方法都是同步方法，但是sendSmg()是被phone1调用的，锁住的是phone1这个对象，
     * 而call()是phone2调用的，锁着的对象是phone2，也即有两把锁，这两个方法不存在竞争关系
     */




}