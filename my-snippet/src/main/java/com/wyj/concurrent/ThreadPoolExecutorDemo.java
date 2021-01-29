package com.wyj.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadPoolExecutor工作机制：
 * 当添加一个任务时，
 * 1.如果当前线程数未超过核心线程数，则创建一个新的线程执行任务
 * 2.如果当前线程数超过核心线程数，则优先将任务放到阻塞队列中，直至队列塞满
 * 3.如果队列阻塞队列已经塞满，且当前线程数未超过最大线程数，则创建一个新的线程执行任务
 * 4.如果队列阻塞队列已经塞满，且当前线程数已等于最大线程数，则根据拒绝策略拒绝执行任务
 * <p>
 * Note:
 * 1.当corePoolSize=maximumPoolSize时，线程池就是一个固定数量的线程池
 * 2.使用LinkedBlockingQueue可以创建一个无界的阻塞队列，但是也会有OOM的风险
 *
 * @author W.Y.J
 * @Date 2021/1/29 15:30
 */
public class ThreadPoolExecutorDemo {


    public static void main(String[] args) {

        /*阻塞队列的容量*/
        Integer queueCapacity = 2;

        /*线程池的核心线程数*/
        Integer corePoolSize = 2;

        /*线程池的最大线程数*/
        Integer maximumPoolSize = 4;

        /**
         * 如果当前线程数大于核心线程数，超出核心线程数的空闲线程
         * 在未接到新的task时的最大存活时间
         */
        Long keepAliveTime = 3000L;


        BlockingQueue queue = new LinkedBlockingQueue<Runnable>(queueCapacity);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                queue,
                new MyThreadFactory(),
                new MyIgnorePolicy());
        for (int i = 1; i < 10; i++) {
            threadPoolExecutor.submit(new MyTask("Task" + i));
        }
        /**
         * 结果描述：
         * 1，由于线程预启动，首先创建了1，2号线程，然后task1，task2被执行；
         * 2，但任务提交没有结束，此时任务task3，task6到达发现核心线程已经满了，进入等待队列；
         * 3，等待队列满后创建任务线程3，4执行任务task3，task6，同时task4，task5进入队列；
         * 4，此时创建线程数（4）等于最大线程数，且队列已满，所以7，8，9，10任务被拒绝；
         * 5，任务执行完毕后回头来执行task4，task5，队列清空。
         */
    }


    /**
     * 实现拒绝策略
     */
    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.err.println(r.toString() + " rejected");
        }
    }


    /**
     * 实现产生线程的工厂类
     */
    public static class MyThreadFactory implements ThreadFactory {

        //定义一个自增的变量，作为线程的标识
        private AtomicInteger inc = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            int id = inc.getAndIncrement();
            System.out.println("Thread" + id + " is Created");
            return new Thread(r, "Thread" + id);
        }
    }

    /**
     * 实现需要执行的任务
     */
    public static class MyTask implements Runnable {

        private String name;

        @Override
        public void run() {
            System.out.println(name + "  is Running");
            try {

                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }
}
