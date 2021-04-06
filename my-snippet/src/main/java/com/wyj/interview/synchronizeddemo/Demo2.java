package com.wyj.interview.synchronizeddemo;

import java.util.concurrent.TimeUnit;
/**
 * Phone对象只一个方法被synchronized修饰，执行的先后顺序？
 * @author W.Y.J
 * @Date 2021/1/17 17:17
 */
public class Demo2 {

    public static void main(String[] args) {
        Phone2 phone = new Phone2();
        new Thread(() -> phone.sendMsg(), "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> phone.call(), "B").start();

        //Q:输出结果是什么？？

    }

}

class Phone2 {
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
     * 与demo1输出的结果是不一致的，这是因为call方法并没有被synchronized修饰，所以它不需要等sendMsg的锁释放
     * 直接就可以执行
     */




}