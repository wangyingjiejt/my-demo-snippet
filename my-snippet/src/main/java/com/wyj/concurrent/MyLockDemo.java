package com.wyj.concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class MyLockDemo {


    class MyTask implements Runnable {

        int count = 0;

        MyLock lock = new MyLock();

        //        ReentrantLock lock =new ReentrantLock();
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                try {
                    lock.lock();
                    count++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    @Test
    public void noLockTest() {

        MyTask task = new MyTask();
        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
        new Thread(task, "t3").start();
        new Thread(task, "t4").start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(task.count);
    }

    private static final class MyLock extends AbstractQueuedLongSynchronizer {

        public void lock() {
            acquire(1);
        }

        public void unlock() {
            release(1);
        }

        @Override
        protected boolean tryAcquire(long arg) {
            //获取同步器的状态
            long c = getState();
            Thread thread = Thread.currentThread();
            System.out.println("线程：" + thread.getName() + "想要获取锁");
            if (c == 0) {
                /**
                 * 如果阻塞队列为空，且更改状态完成，即获取到锁了
                 * 这里是公平锁的思想，如果想实现非公平锁，则无需判断直接参与锁竞争
                 */
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, arg)) {

                    //设置当前线程独占，这个方法并没有使用cas，因为上一步已获取到锁，这一步就不存在竞争，（偏向锁）
                    setExclusiveOwnerThread(thread);
                    System.out.println("线程：" + thread.getName() + "获取锁>>>");
                    return true;
                }
            }
            //TODO: 2021/4/1 这实现的是一个不可重入的锁 ，如果需要实现可重入，可以判断当前持有锁定的线程是否与申请线程相同
            return false;
        }

        @Override
        protected boolean tryRelease(long arg) {
            long c = getState() - arg;
            //只能释放自己上的锁
            Thread thread = Thread.currentThread();
            System.out.println("线程：" + thread.getName() + "释放锁<<<");
            if (getExclusiveOwnerThread() != thread)
                throw new IllegalMonitorStateException();
            boolean free = false;
            setState(c);
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            return free;
        }
    }

}
