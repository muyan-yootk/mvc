package com.yootk.common.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD}) // 只能够用于类和方法的定义上
@Retention(RetentionPolicy.RUNTIME) // 此注解在运行时有效
public @interface RequestMapping { // 控制层访问路径
    public String value() default "/" ; // 访问路径
}
