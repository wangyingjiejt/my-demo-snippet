package com.wyj.reflect_demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模拟没有batis的Select注解
 * @author wangyj
 * @date 2020/5/13 16:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ImitateSelect {
    
    String value();
}
