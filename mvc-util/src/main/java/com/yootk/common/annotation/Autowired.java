package com.yootk.common.annotation;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD}) // 只能够用于类的定义上
@Retention(RetentionPolicy.RUNTIME) // 此注解在运行时有效
public @interface Autowired { // 依赖注解
    public String name() default "none" ;  // 免属性名称配置的
}
