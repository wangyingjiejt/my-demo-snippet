package com.wyj.design_pattern.decorator;

/**
 *
 * 实现抽象构件的具体构件
 * @Author W.Y.J
 * @Date 2021/4/25 22:57
 */
public class ConcreteComponent implements Component{

    @Override
    public void doSomething() {
        System.out.println("基础动画");
    }
}
