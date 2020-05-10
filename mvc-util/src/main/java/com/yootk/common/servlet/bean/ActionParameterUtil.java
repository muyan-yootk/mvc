package com.yootk.common.servlet.bean;

import com.yootk.common.bean.MethodParameterUtil;

import java.lang.reflect.Method;
import java.util.Map;

public class ActionParameterUtil { // Action参数的处理
    /**
     * 获取指定Action类对象中的方法参数以及对应的参数的内容
     * @param actionObject Action实例化对象
     * @param method 要执行的Action方法
     * @return 方法对应的参数（转换后的参数）
     */
    public static Object[] getMethodParameterValue(Object actionObject, Method method) {
        Object [] result = null ; // 返回的是参数的内容
        // 此时根据Action类型以及对应要调用的方法来获取方法中的参数名称以及类型的信息
        Map<String, Class> paramMap = MethodParameterUtil.getMethodParameter(actionObject.getClass(), method);
        if (paramMap.size() == 0) { // 当前调用的方法上没有参数定义
            result = new Object[]{}; // 空对象数组
        } else {    // 现在有参数
            result = new Object[paramMap.size()] ; // 解析出来的参数个数就是返回对象数组长度
            int foot = 0 ;
            for (Map.Entry<String, Class> entry : paramMap.entrySet()) { // 迭代Map集合
                result[foot ++] = DataTypeConverterUtil.convert(entry.getKey(), entry.getValue()) ;
            }
        }
        return result ;
    }
}
