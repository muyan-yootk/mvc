package com.yootk.common.factory;

import com.yootk.common.bean.DependObject;
import com.yootk.common.proxy.ServiceProxy;
import com.yootk.common.util.WebObjectUtil;

import java.lang.reflect.InvocationTargetException;

public class Factory { // 业务工厂类
    public static Object getServiceInstance(Class<?> serviceClass) {
        Object aspectObject = null ;
        String aspectClass = WebObjectUtil.getApplication().getInitParameter("aspectClass") ;
        if (aspectClass != null) {  // 配置了业务层的处理机制
            try {
                aspectObject = Class.forName(aspectClass).getConstructor().newInstance() ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Object realObject = serviceClass.getConstructor().newInstance() ;
            new DependObject(realObject); // 工厂类中处理的是业务对象
            return new ServiceProxy().bind(realObject, aspectObject) ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    public static Object getDAOInstance(Class<?> daoClass) {    // 获取DAO接口对象
        try {
            return daoClass.getConstructor().newInstance() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
}
