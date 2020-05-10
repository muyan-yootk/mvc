package com.yootk.common.proxy;

public interface ProxyAspect {
    public default void before() {} // 在调用之前执行的某些操作
    public default void after() {}  // 在调用之后执行的某些操作
    public default void exception() {} // 出现异常之后执行的某些操作
}
