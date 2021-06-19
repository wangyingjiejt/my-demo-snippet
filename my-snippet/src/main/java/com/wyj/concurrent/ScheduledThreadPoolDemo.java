package com.wyj.concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程池
 *
 * @author W.Y.J
 * @Date 2021/6/19 4:16 下午
 */
public class ScheduledThreadPoolDemo {


    @Test
    public void test1() throws InterruptedException {
        CountDownLatch cdl=new CountDownLatch(1);
        //创建线程大小为5的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i <3; i++) {

            Worker worker = new Worker("task-"+i);
            System.out.println("worker-"+worker.getName()+" 加入时间 "+new Date());
            //这个方法是在设置的delay时间后，只执行一次任务
            scheduledExecutorService.schedule(worker,5,TimeUnit.SECONDS);
        }

        cdl.await();//防止子线程没有执行完就退出程序
    }


    @Test
    public void test2() throws InterruptedException {
        CountDownLatch cdl=new CountDownLatch(1);
        //创建线程大小为5的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i <3; i++) {

            Worker worker = new Worker("task-"+i);
            System.out.println("worker-"+worker.getName()+" 加入时间 "+new Date());
            //这个方法是在设置的delay时间后，每隔period执行一次
            scheduledExecutorService.scheduleAtFixedRate(worker,5,2,TimeUnit.SECONDS);
        }

        cdl.await();//防止子线程没有执行完就退出程序
    }

    @Test
    public void test3() throws InterruptedException {
        CountDownLatch cdl=new CountDownLatch(1);
        //创建线程大小为5的线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        for (int i = 0; i <3; i++) {

            Worker worker = new Worker("task-"+i);
            System.out.println("worker-"+worker.getName()+" 加入时间 "+new Date());
            //这个方法是在设置的delay时间后，delay后再执行一次
            scheduledExecutorService.scheduleWithFixedDelay(worker,5,2,TimeUnit.SECONDS);
        }

        cdl.await();//防止子线程没有执行完就退出程序
    }


    static class Worker implements Runnable {

        private String name;

        public String getName() {
            return name;
        }

        public Worker(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("name="+name+",startTime = "+new Date());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("name="+name+",endTime = "+new Date());
        }
    }
}
