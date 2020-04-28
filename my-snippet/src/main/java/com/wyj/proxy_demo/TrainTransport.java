package com.wyj.proxy_demo;


/**
 * 铁路运输
 * @author wangyj
 * @Date 2020/4/2 10:13 上午
 */
public class TrainTransport implements Express {

    @Override
    public void deliveryType() {
        System.out.println("使用铁路运输快递...");
    }
}
