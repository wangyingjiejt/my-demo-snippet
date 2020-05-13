package com.wyj.reflect_demo;

import java.lang.reflect.Proxy;
import java.util.Arrays;

interface MyMapper{
    @ImitateSelect("select * from user where id=#{}")
    String getUser(String id);
}



public class SelectDemo {
    public static void main(String[] args) {
        MyMapper myMapperImp = (MyMapper) Proxy.newProxyInstance(SelectDemo.class.getClassLoader(), new Class[]{MyMapper.class}, (proxy, method, args1) -> {
            //获取注解传递的值
            System.out.println(method.getAnnotation(ImitateSelect.class).value());
            System.out.println(Arrays.asList(args1).toString());

            return null;
        });

        myMapperImp.getUser("123");
    }
}

