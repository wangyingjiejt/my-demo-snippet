package com.wyj.forkjoin_demo;

import java.util.concurrent.RecursiveTask;

/**
 * fork、join计算数组内数字和
 */
public class SumRecursiveTask extends RecursiveTask<Long> {

    private static final int threshold = 20;

    private int[] numArr;

    private int left;

    private int right;


    public SumRecursiveTask(int[] numArr, int left, int right) {
        this.numArr = numArr;
        this.left = left;
        this.right = right;
    }

    @Override
    protected Long compute() {

        long sum = 0;
        if (right - left <= threshold) {
            for (int i = left; i < right; i++) {
                sum += numArr[i];
            }
            System.out.println(String.format("compute %d~%d = %d", left, right, sum));
            return sum;
        } else {
            int middle = (right + left) / 2;
            System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", left, right, left, middle, middle, right));
            SumRecursiveTask subTask1 = new SumRecursiveTask(numArr, left, middle);
            SumRecursiveTask subTask2 = new SumRecursiveTask(numArr, middle, right);
            invokeAll(subTask1, subTask2);
            return subTask1.join() + subTask2.join();
        }
    }
}
