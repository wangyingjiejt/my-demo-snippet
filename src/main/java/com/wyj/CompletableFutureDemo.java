package com.wyj;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {


    public static void main(String[] args) {


        //calculate sth...
        try {
//            CompletableFuture<String> future = calculateAsync();
//            CompletableFuture<String> future = CompletableFuture.completedFuture("hello");
            CompletableFuture<String> f1 = CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            });
//            CompletableFuture<String> f2 = f1.thenApply(a->a+"sssss");
            System.out.println("~~~~");
            System.out.println(f1.get());
            System.out.println("。。。。。。。。。");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


        public static CompletableFuture<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(5000);
            completableFuture.complete("Hello");
            return null;
        });

        System.out.println("--------------");
        return completableFuture;
    }


    public static CompletableFuture<String> calculateAsync2() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Thread.sleep(5000);
//        completableFuture.

        System.out.println("--------------");
        return completableFuture;
    }
}
