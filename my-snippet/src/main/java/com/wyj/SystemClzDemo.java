package com.wyj;

/**
 * System类方法使用demo
 * @author W.Y.J
 * @Date 2021/1/12 15:53
 */
public class SystemClzDemo {

    @org.junit.Test
    public void arrayCopyDemo(){

        Integer[] s1=new Integer[]{1,2,3,4,5};
        Integer[] s2=new Integer[5];
        System.arraycopy(s1,2,s2,1,3);
        for (Integer i : s2) {
            System.out.println(i);
        }
        /**
         * print result:
         * null
         * 3
         * 4
         * 5
         * null
         */
    }
}
