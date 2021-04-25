package com.wyj.design_pattern.decorator;

/**
 * @Author W.Y.J
 * @Date 2021/4/25 23:05
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        //对原有方法进行装饰
        DecoratorComponent decorator=new DecoratorComponent(component);
        decorator.doSomething();
        System.out.println("-------- I am split line--------");
        //装饰器可以组合使用，再次嵌套，提现装饰器模式的灵活性
        Decorator2Component decorator2=new Decorator2Component(decorator);
        decorator2.doSomething();
    }
}
