package com.yootk.common.bean;

import java.lang.reflect.Method;

public class MethodUtil {
    private MethodUtil() {}

    /**
     * 根据指定的类型获取指定名称的Method对象，这样的操作是为了解决不确定方法参数类型的情况
     * @param clazz 要进行反射操作的Class类
     * @param methodName 方法名称
     * @return 如果方法存在以Method实例形式返回，如果不存在返回null
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                return method ;
            }
        }
        return null ;
    }
}
