package com.wyj.springenv.demos.web.observer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @Author W.Y.J
 * @Date 2021/4/14 23:19
 */

@Service
public class UserService implements ApplicationEventPublisherAware {



    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.publisher=applicationEventPublisher;
    }

    public void register(String username) {
        // ... 执行注册逻辑
        System.out.println("用户["+username+"]进行注册，时间["+System.currentTimeMillis()+"]");
        // <2> ... 发布
        publisher.publishEvent(new UserRegisterEvent(this, username));
    }

}
