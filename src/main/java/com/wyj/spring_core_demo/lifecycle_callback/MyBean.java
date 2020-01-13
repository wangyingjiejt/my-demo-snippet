package com.wyj.spring_core_demo.lifecycle_callback;

import javax.annotation.PostConstruct;

public class MyBean {

    private OtherBean otherBean;


    public MyBean() {
        System.out.println("Mybean constructor "+this);
    }

    @PostConstruct
    public void  postConstructMethod(){
        System.out.println("Mybean postConstruct"+this);
    }
}
