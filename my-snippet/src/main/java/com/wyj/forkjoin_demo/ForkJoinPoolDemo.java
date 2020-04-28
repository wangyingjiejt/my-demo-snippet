package com.wyj.forkjoin_demo;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolDemo {
    public static void main(String[] args) {


        ForkJoinPool commonPool = ForkJoinPool.commonPool();


//        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("i am chinese");
//        commonPool.submit(customRecursiveAction);

//        List<Integer> numList = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

        int length = 100000;
        int[] numArr = new int[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = i + 1;
        }
        SumRecursiveTask sumRecursiveTask = new SumRecursiveTask(numArr, 0, length);
        Long result = commonPool.submit(sumRecursiveTask).invoke();
        System.out.println(result);
    }
}
