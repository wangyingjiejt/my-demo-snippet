package com.wyj;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Google使用令牌桶实现限流--RateLimiter
 */
public class RateLimiterDemo {


    public static void main(String[] args) {
//        RateLimiterDemo.testSmoothBursty();
        RateLimiterDemo.testSmoothBursty3();
        
    }


    public static void testSmoothBursty3() {
        RateLimiter r = RateLimiter.create(3);
        while (true)
        {
            System.out.println("get 5 tokens: " + r.acquire(5) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("get 1 tokens: " + r.acquire(1) + "s");
            System.out.println("end");
            /**
             * output:
             * get 5 tokens: 0.0s
             * get 1 tokens: 0.996766s As a result of the delayed processing method, this thread waits for additional time on behalf of a previous thread.
             * get 1 tokens: 0.194007s
             * get 1 tokens: 0.196267s
             * end
             * get 5 tokens: 0.195756s
             * get 1 tokens: 0.995625s As a result of the delayed processing method, this thread waits for additional time on behalf of a previous thread.
             * get 1 tokens: 0.194603s
             * get 1 tokens: 0.196866s
             */
        }
    }


    public static void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(2);
        while (true) {
            System.out.println("get 1 tokens: " + r.acquire() + "s");
            try {
                Thread.sleep(2000);
            } catch (Exception e) {}
        }
        /**
         * output: Basically, the limiter is executed every 0.2s, which complies with the setting of releasing five tokens per second.
         * get 1 tokens: 0.0s
         * get 1 tokens: 0.182014s
         * get 1 tokens: 0.188464s
         * get 1 tokens: 0.198072s
         * get 1 tokens: 0.196048s
         * get 1 tokens: 0.197538s
         * get 1 tokens: 0.196049s
         */
    }
}
