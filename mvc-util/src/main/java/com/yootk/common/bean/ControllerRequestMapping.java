package com.yootk.common.bean;

import java.lang.reflect.Method;

public class ControllerRequestMapping {
    private Class<?> actionClazz; // Action程序类实例
    private Method actionMethod; // 控制层方法
    public ControllerRequestMapping(Class<?> actionClazz, Method actionMethod) {
        this.actionClazz = actionClazz;
        this.actionMethod = actionMethod;
    }
    public Class<?> getActionClazz() {
        return actionClazz;
    }
    public Method getActionMethod() {
        return actionMethod;
    }
}
