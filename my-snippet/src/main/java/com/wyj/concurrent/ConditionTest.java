package com.wyj.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Note:
 * 1、Condition必须要配合Lock使用
 * 2、Condition提供await()方法，能够阻塞当前队列，并提供signal()方法支持从另一个线程
 * 唤醒已经阻塞的线程
 * <p>
 * 模拟实现生产者\消费者模型
 */
public class ConditionTest {

    public static void main(String[] args) {
        /**
         * 模拟场景场景中存在两条生产线程t1和t2用于生产，也存在两条消费线程t3，t4用于消费，
         * 需要保证只有在生产线程产生产品后，消费线程才能消费，否则只能等待，直到生产线程产生新的后唤醒消费线程，不能重复消费
         */
        ProductFactory pf = new ProductFactory();
        Thread t1 = new Thread(() -> {
            pf.produce("铜锣烧");
        });

        Thread t2 = new Thread(() -> {
            pf.produce("铜锣烧");
        });
        Thread t3 = new Thread(() -> {
            pf.consumer();
        });
        Thread t4 = new Thread(() -> {
            pf.consumer();
        });
        Thread t5 = new Thread(() -> {
            pf.produce("铜锣烧");
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }


}


/**
 * 产品工厂
 *
 * @author W.Y.J
 * @Date 2021/1/5 18:54
 */
class ProductFactory {

    //商品名
    private String productName;

    //商品编号
    private Integer productNo = 1;

    //是否又库存
    private Boolean hasStock = false;

    //给工厂创建一把锁，防止并发（重复生产、重复消费）
    ReentrantLock lock = new ReentrantLock();

    //通过已有的锁，创建两个监视器，一个负责生产监视、一个负责消费监视
    Condition produceCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();


    public void produce(String name) {

        while (true) {
            //先获取锁
            lock.lock();
            try {
                //有库生产者阻塞等待
                if (hasStock) {
                    produceCondition.await();
                }
                //生产商品
                this.productName = name + "-" + productNo;
                System.out.println("---工人["+Thread.currentThread().getName()+"]+生产商品:" + productName + "---");
                //置为有库存
                hasStock = true;
                productNo++;
                //唤醒消费线程，可以进行消费
                consumerCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
        }
    }

    public void consumer() {

        while (true) {
            //先获取锁
            lock.lock();
            try {
                if (!hasStock) {
                    //没有库存了，等待产品
                    consumerCondition.await();
                }
                //消费商品
                System.err.println("---工人["+Thread.currentThread().getName()+"]+消费商品:" + productName + "---");
                //吃完休息一会儿
                Thread.sleep(500);
                //置为无库存
                hasStock = false;
                //唤醒生产线程搞生产
                produceCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
        }
    }
}