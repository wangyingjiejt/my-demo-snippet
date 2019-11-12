package com.wyj.reflect_demo;

import java.lang.reflect.Field;

public class demo {

    public static void main(String[] args) {


        B b =new B();
        Field[] fs = B.class.getFields();
        for (Field f:fs){
            f.setAccessible(true);
            try {
                System.out.println(f.get(b));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


//        Field[] fs = B.class.getDeclaredFields();
//        for (Field f:fs){
//            System.out.println(f.toGenericString());
//        }


        String name ="sfsjfalsdfj.bmp";
        System.out.println(name.substring(name.lastIndexOf(".")+1));

    }
}
