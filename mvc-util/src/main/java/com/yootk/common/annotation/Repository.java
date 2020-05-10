package com.yootk.common.annotation;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE}) // 只能够用于类的定义上
@Retention(RetentionPolicy.RUNTIME) // 此注解在运行时有效
public @interface Repository { // 数据层注解
    public String value() default "none" ;  // 免属性名称配置的
}
