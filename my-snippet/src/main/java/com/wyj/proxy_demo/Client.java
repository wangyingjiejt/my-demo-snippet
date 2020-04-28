package com.wyj.proxy_demo;

public class Client {
    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //jdk代理
        ExpressProxy proxy = new ExpressProxy();

        //使用汽车运输
        System.out.println("使用汽车运输---------");
        CarTransport carTransport = new CarTransport();
        proxy.setTarget(carTransport);
        Express express = (Express) proxy.getProxy();
        express.deliveryType();

        System.out.println("使用铁路运输---------");
        TrainTransport trainTransport = new TrainTransport();
        proxy.setTarget(trainTransport);
        Express express2 = (Express) proxy.getProxy();
        express2.deliveryType();
    }
}
