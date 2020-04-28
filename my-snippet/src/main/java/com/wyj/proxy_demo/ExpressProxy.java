package com.wyj.proxy_demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 快递代理类
 *
 * @author wangyj
 * @Date 2020/4/2 10:17 上午
 */
public class ExpressProxy implements InvocationHandler {

    private Object target;

    /**
     * 获取代理类
     * @author wangyj
     * @Date 2020/4/2 10:57 上午
     * @param
     * @return java.lang.Object
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始装备货物");
        Object result = method.invoke(target, args);
        System.out.println("开始卸货");
        return result;
    }


    public void setTarget(Object target) {
        this.target = target;
    }
}
