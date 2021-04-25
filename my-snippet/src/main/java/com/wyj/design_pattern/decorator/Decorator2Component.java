package com.wyj.design_pattern.decorator;

/**
 * 装饰器2，注意装饰器必须和被装饰对象实现同一个接口
 * @Author W.Y.J
 * @Date 2021/4/25 22:58
 */
public class Decorator2Component implements Component {

    private Component component;

    public Decorator2Component(Component comp) {
        this.component = comp;
    }

    @Override
    public void doSomething() {
        component.doSomething();
        //装饰后的拓展内容
        System.out.println("加滤镜");
    }
}
