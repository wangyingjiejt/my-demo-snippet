package com.wyj.springenv.demos.web.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;

/**
 * 给用户发送邮件
 * @Author W.Y.J
 * @Date 2021/4/14 22:33
 */
@Service
public class SendEmailListener implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {

        System.out.println("给用户["+event.getUsername()+"]发邮件,时间["+event.getTimestamp()+"]");
    }
}
