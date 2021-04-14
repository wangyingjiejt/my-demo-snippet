package com.wyj.springenv.demos.web.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 发送优惠券
 * 也可以使用@EventListener注解代替实现ApplicationListener接口
 * @Author W.Y.J
 * @Date 2021/4/14 22:37
 */
@Service
@Order(2)
public class SendCoupons  {


    @EventListener
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.println("给用户["+event.getUsername()+"]发优惠券,时间["+event.getTimestamp()+"]");
    }
}
