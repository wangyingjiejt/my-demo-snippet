package com.wyj.interview;

import org.junit.Test;

public class BinarySearch {

    private Integer[] arr = new Integer[]{1, 2, 22, 23, 34, 67, 100};

    @Test
    public void test1() {
        Integer target = 1;
        int l = 0;
        int r = arr.length-1;
        while (l <= r) {
        int m = l+(r - l) / 2;
            if (arr[m] == target) {
                System.out.println("已找到该元素，下标：" + m);
                return;
            } else if (arr[m] > target) {
                //在左半
                r = m - 1;
            } else {
                //在右半
                l = m + 1;
            }
        }
        System.out.println("未找到该元素");
    }
}
