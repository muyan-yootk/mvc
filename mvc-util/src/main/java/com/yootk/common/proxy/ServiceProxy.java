package com.yootk.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ServiceProxy implements InvocationHandler {
    private Object target ;
    private ProxyAspect aspect ; // 代理辅助操作
    public Object bind(Object object, Object aspectObject) {
        this.target = object ; // 保存真实对象
        if (aspectObject instanceof ProxyAspect) { // 防止类转换异常
            this.aspect = (ProxyAspect) aspectObject; // 保存处理对象
        }
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this) ;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null ;
        try {
            if (this.aspect != null) {
                this.aspect.before(); // 之前执行的某些操作
            }
            result = method.invoke(this.target, args) ; // 代理调用真实操作
            if (this.aspect != null) {
                this.aspect.after(); // 之后执行的某些操作
            }
        } catch (Exception e) {
            if (this.aspect != null) {
                this.aspect.exception(); // 异常处理
            }
        }
        return result;
    }
}
