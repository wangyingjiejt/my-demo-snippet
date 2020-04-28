package com.wyj.proxy_demo;


/**
 * 汽车运输
 * @author wangyj
 * @Date 2020/4/2 10:13 上午
 */
public class CarTransport implements Express {

    @Override
    public void deliveryType() {
        System.out.println("使用汽车运输快递...");
    }
}
