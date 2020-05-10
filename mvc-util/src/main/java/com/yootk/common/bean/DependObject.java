package com.yootk.common.bean;

import com.yootk.common.annotation.Autowired;
import com.yootk.common.factory.Factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DependObject { // 实现具体的注入操作结构
    // 【控制层】控制层需要业务层的对象实例注入，此时的targetObject是一个Action实例
    // 【业务层】业务层需要数据层的对象实例注入，此时的targetObject是一个Service实例
    private Object targetObject ;
    public DependObject(Object targetObject) { // 对象注入
        this.targetObject = targetObject ;
        this.inject(); // 对象注入
    }
    // 所谓的对象注入就是根据类型或者名称找到匹配的子类的Class对象，找到所有可能存在有Autowired注解
    private void inject() { // 对象注入操作
        Field[] fields = this.targetObject.getClass().getDeclaredFields();// 获取全部的成员属性
        for (Field field : fields) {    // 成员信息的输出
            if (field.isAnnotationPresent(Autowired.class)) {  // 该成员上拥有依赖注入的配置
                Autowired autowired = field.getAnnotation(Autowired.class) ; // 获取指定注解信息
                boolean service_flag = true ; // 默认为Service实例获取
                Class<?> injectClazz = null ; // 要注入的对象类的Class
                if ("none".equals(autowired.name())) {  // 没有配置依赖的名称
                    injectClazz = ScannerPackageUtil.getServiceMap().get(field.getType()) ; // 通过Service获取对象
                    if (injectClazz == null) {  // 没有这个信息
                        service_flag = false ; // 不再通过Service获取
                        injectClazz = ScannerPackageUtil.getDaoMap().get(field.getType()) ; // 通过DAO获取
                    }
                } else {    // 配置名称
                    injectClazz = ScannerPackageUtil.getByNameMap().get(autowired.name()) ;
                }
                if (injectClazz != null) {  // 已经找到了匹配的Class实例
                    try {
                        Object injectObject = null ;
                        if (service_flag) {
                            injectObject = Factory.getServiceInstance(injectClazz); // 反射实例化对象
                        } else {
                            injectObject = Factory.getDAOInstance(injectClazz) ; // 反射获取DAO对象
                        }
                        if (injectObject != null) {
                            field.setAccessible(true); // 取消封装
                            field.set(this.targetObject, injectObject); // 自动注入
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
