package com.wyj.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {


    public static void main(String[] args) {
        FutureTaskDemo.Counter counter =new FutureTaskDemo.Counter();
        FutureTask futureTask=new FutureTask(counter);
        new Thread(futureTask).start();
        try {
            Object o = futureTask.get();
            Object o2 = futureTask.get();
            System.out.println("get-----");
            System.out.println(o);
            System.out.println("get-----");
            System.out.println(o2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static class Counter implements Callable{

        @Override
        public Object call() throws Exception {
            for (int i=1;i<100;i++){
                System.out.println(i);
            }
            return true;
        }
    }
}


