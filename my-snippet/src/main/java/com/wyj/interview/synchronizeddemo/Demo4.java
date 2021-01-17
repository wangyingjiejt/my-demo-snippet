package com.wyj.interview.synchronizeddemo;

import java.util.concurrent.TimeUnit;

/**
 * Phone的两个方法都被synchronized修饰,并且都被static修饰，但是new两个对象分别调用两个方法，执行顺序？
 * @author W.Y.J
 * @Date 2021/1/17 17:18
 */
public class Demo4 {

    public static void main(String[] args) {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();
        new Thread(() -> phone1.sendMsg(), "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> phone2.call(), "B").start();

    }

}

class Phone4 {
    public static synchronized void sendMsg() {

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send msg...");
    }

    public static synchronized void call() {

        System.out.println("call other...");
    }














    /*****************************************************解析**************************************************/


    /**
     * print result:
     * send msg...
     * call other...
     * 结果可以看出，两个方法存在先后拿锁的现象，也即使用的是同一把锁，这是为何？
     * 与demo3不同的是，这两个方法是静态方法，这种情况下，synchronized锁住的是Class，
     * 不管有多少个实例，所有的静态方法都要共享的都是同一把锁。
     */




}