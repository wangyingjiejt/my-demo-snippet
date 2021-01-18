package com.wyj.concurrent;

import org.junit.Test;

import java.util.concurrent.*;
/*
    CyclicBarrier对比CountdownLatch？
    用途：
    CyclicBarrier   一个线程等待其他线程或者其他的一组线程执行完毕后执行需要（一个等多个或者一组操作）
    CountdownLatch  通过它可以实现让一组线程等待至某个状态之后全部同时执行 （一个和同一组内的其他相互等待）
    两个类在很多场景下都能实现同样的功能，使用上的区别是countdownLatch是减法计数器，cyclicBarrier是加法计数器
    实现：
    CyclicBarrier  内部是用ReentrantLock配合Condition实现的
    CountdownLatch 内部是AQS实现
 */


/**
 * 使用CyclicBarrier模拟学生考试排名
 * 计算分前，需要使用CyclicBarrier保证每个学生交卷
 * @author W.Y.J
 * @Date 2021/1/12 17:39
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //学生成绩单
        CopyOnWriteArrayList<Student> scoreRank = new CopyOnWriteArrayList();
        Integer studentNum = 10;
        //模拟10个人答题，根据算出排名
        CyclicBarrier cb=new CyclicBarrier(studentNum,()->{
            //十个学生答完题后
            //计算得分，排名
            scoreRank.stream()
                    .sorted((s1,s2)->s2.getScore()-s1.getScore())
                    .forEach(System.out::println);
        });

        for (int i = 1; i <= studentNum; i++) {
            new Thread(() -> {
                //睡眠模拟作答时间
                try {
                long l = (long) (Math.random() * 100);
                TimeUnit.MILLISECONDS.sleep(l);
                int score=(int) (Math.random() * 100 + 1);
                Student student = new Student(Thread.currentThread().getName() + "个同学",score);
                scoreRank.add(student);
                //每执行完成之后cb等待其他人答卷完成
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private  void doTesting() {
        //睡眠模拟作答时间
        long l = (long) (Math.random() * 100);
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   static class Student {

        private String name;

        private Integer score;

        public Student(String name, Integer score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "name='" + name + '\'' +
                    ", score=" + score ;
        }
    }
}
