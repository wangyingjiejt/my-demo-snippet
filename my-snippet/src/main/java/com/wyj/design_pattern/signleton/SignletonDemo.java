package com.wyj.design_pattern.signleton;

public class SignletonDemo {

    public static void main(String[] args) {

        SignletonEnum instance = SignletonEnum.INSTANCE;
        SignletonEnum instance2 = SignletonEnum.INSTANCE;
        System.out.println(instance==instance2);
    }
}
