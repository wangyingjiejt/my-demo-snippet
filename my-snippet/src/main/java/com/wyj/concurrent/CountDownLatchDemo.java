package com.wyj.concurrent;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;


/**
 * 使用CountDownLatch模拟学生考试排名
 * 计算分前，需要使用CountdownLatch保证每个学生交卷（线程都执行完）
 * @author W.Y.J
 * @Date 2021/1/12 17:39
 */
public class CountDownLatchDemo {

    @Test
    public void test() throws InterruptedException {
        //学生成绩单
        CopyOnWriteArrayList<Student> scoreRank = new CopyOnWriteArrayList();
        Integer studentNum = 10;
        //模拟10个人答题，根据算出排名
        CountDownLatch dl = new CountDownLatch(studentNum);
        for (int i = 1; i <= studentNum; i++) {
            new Thread(() -> {
                doTesting();
                Student student = new Student(Thread.currentThread().getName() + "个同学", getRandomScore());
                scoreRank.add(student);
                dl.countDown();
            }).start();
        }
        //等待所有的线程都执行完毕，才继续进行算分排名的逻辑
        dl.await();
        //计算得分，排名
        scoreRank.stream()
                .sorted((s1,s2)->s2.getScore()-s1.getScore())
                .forEach(System.out::println);
    }

    private  Integer getRandomScore() {
        return (int) (Math.random() * 100 + 1);
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

    class Student {

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
