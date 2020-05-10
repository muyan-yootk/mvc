package com.yootk.common.bean;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class MethodParameterUtil { // 对方法中的参数进行解析处理
    /**
     * 根据给定的Class类型以及方法名称进行方法参数的解析
     * @param clazz Class类型
     * @param methodName 方法名称
     * @return 将方法名称和方法的类型直接通过Map集合进行保存，key为参数名称，value为参数类型
     */
    public static Map<String, Class> getMethodParameter(Class<?> clazz, String methodName) {
        return getMethodParameter(clazz, MethodUtil.getMethod(clazz, methodName)) ;
    }
    /**
     * 根据给定的Class类型以及方法名称进行方法参数的解析
     * @param clazz Class类型
     * @param method 方法定义
     * @return 将方法名称和方法的类型直接通过Map集合进行保存，key为参数名称，value为参数类型
     */
    public static Map<String, Class> getMethodParameter(Class<?> clazz, Method method) {
        Map<String, Class> map = new LinkedHashMap<>() ; // 参数名称一定要有序存储
        // 通过Spring开发包获取指定方法全部参数的名称
        String[] paramNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method) ;
        // 方法参数的类型可以直接通过Java原生反射机制获取
        Class<?> paramTypes [] = method.getParameterTypes(); // 获取方法参数类型
        for (int x = 0; x < paramNames.length; x++) {
            map.put(paramNames[x], paramTypes[x]);
        }
        return map ;
    }
}
