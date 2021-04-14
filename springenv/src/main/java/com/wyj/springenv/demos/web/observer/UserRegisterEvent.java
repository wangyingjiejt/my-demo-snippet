package com.wyj.springenv.demos.web.observer;

import org.springframework.context.ApplicationEvent;

/**
 * 创建一个用户注册事件
 * @Author W.Y.J
 * @Date 2021/4/14 22:12
 */
public class UserRegisterEvent extends ApplicationEvent {


    private String username;

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
