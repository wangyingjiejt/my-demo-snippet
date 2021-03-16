package com.wyj;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OOMTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 3; i < Integer.MAX_VALUE; i++) {
            final int num = i;
            executor.execute(() -> isPrimeNumber(num));
//            isPrimeNumber(i);
        }


    }

    public static void isPrimeNumber(int num) {
        if (num < 2 || num % 2 == 0) {
            return;
        }
        for (int i = 3; i * i <= num; i++) {
            if (num % i == 0) {
//                System.out.println(num + "不是质数");
                return;
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num + "是质数");
    }
}
