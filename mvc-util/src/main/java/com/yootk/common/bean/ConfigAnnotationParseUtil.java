package com.yootk.common.bean;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.Repository;
import com.yootk.common.annotation.RequestMapping;
import com.yootk.common.annotation.Service;
import com.yootk.common.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
// 每一个Action类的Class都需要进行解析，而后返回解析的结果
public class ConfigAnnotationParseUtil { // 解析所有的配置注解
    // 此时定义的集合是依据具体的名称来作为key，同时保存有对应的类的Class对象实例
    private Map<String, Class> nameAndTypeMap = new HashMap<>();
    // 此时保存的是所有业务层的接口Class实例以及对应的子类的实例Map集合
    private Map<Class, Class> serviceInterfaceAndClassMap = new HashMap<>() ;
    // 此时保存的是所有数据层接口的Class实例以及对应的子类的Map集合
    private Map<Class, Class> daoInterfaceAndClassMap = new HashMap<>() ;
    private Map<String, ControllerRequestMapping> result = new HashMap<>() ; // 路径和Action映射
    private Class<?> clazz ; // 保存要处理的Class实例
    private String parentUrl = "" ; // 保存父路径
    public ConfigAnnotationParseUtil(Class<?> clazz) {  // Annotation的解析依靠的是反射机制
        this.clazz = clazz ; // 保存实例
        this.classHandle(); // 类信息处理
    }
    private void classHandle() {    // 进行各种注解的区分操作
        Annotation annotations[] = this.clazz.getAnnotations() ; // 获取类中的全部注解
        for (Annotation annotation : annotations) { // 实现注解的循环操作
            if (annotation.annotationType().equals(Controller.class)) { // 当前属于控制层注解
                try {
                    RequestMapping mapping = this.clazz.getAnnotation(RequestMapping.class); // 获取映射路径注解
                    this.parentUrl = mapping.value(); // 获取“父”映射路径
                    if (this.parentUrl.lastIndexOf("/") == -1) {    // 结尾没有“/”
                        this.parentUrl += "/"; // 手工添加一个路径分隔符
                    }
                } catch (Exception e) {}
                this.handleMappingMethod(); // 控制层的方法进行注解解析
            } else if (annotation.annotationType().equals(Service.class)) { // 当前配置的是一个Service注解
                Service service = this.clazz.getAnnotation(Service.class) ; // 获取指定的注解信息
                if ("none".equals(service.value())) {   // 没有配置名字
                    this.nameAndTypeMap.put(StringUtil.firstLower(this.clazz.getSimpleName()), this.clazz);
                } else {    // 明确的配置了名字
                    this.nameAndTypeMap.put(service.value(), this.clazz) ; // 明确使用配置名称
                }
                Class<?> clazzInterfaces[] = this.clazz.getInterfaces(); // 获取当前类实现的全部接口
                for (Class<?> clz : clazzInterfaces) {
                    this.serviceInterfaceAndClassMap.put(clz, this.clazz) ; // 接口和子类的映射
                }
            } else if (annotation.annotationType().equals(Repository.class)) {  // 当前的注解是一个Repository注解
                Repository repository = this.clazz.getAnnotation(Repository.class);
                if ("none".equals(repository.value())) {   // 没有配置名字
                    this.nameAndTypeMap.put(StringUtil.firstLower(this.clazz.getSimpleName()), this.clazz);
                } else {    // 明确的配置了名字
                    this.nameAndTypeMap.put(repository.value(), this.clazz) ; // 明确使用配置名称
                }
                Class<?> clazzInterfaces[] = this.clazz.getInterfaces(); // 获取当前类实现的全部接口
                for (Class<?> clz : clazzInterfaces) {
                    this.daoInterfaceAndClassMap.put(clz, this.clazz) ; // 接口和子类的映射
                }
            }
        }
    }
    private void handleMappingMethod() {    // 控制层里面的每一个方法上也有路径注解
        Method methods[] = this.clazz.getDeclaredMethods(); // 获取全部的方法
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) { // 方法上存在有映射路径的注解
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);// 获取注解
                String path = this.parentUrl + requestMapping.value() ; // 完整映射路径
                this.result.put(path, new ControllerRequestMapping(this.clazz, method)) ;
            }
        }
    }
    public Map<String, ControllerRequestMapping> getResult() {
        return result;
    }

    public Map<Class, Class> getServiceInterfaceAndClassMap() {
        return serviceInterfaceAndClassMap;
    }

    public Map<String, Class> getNameAndTypeMap() {
        return nameAndTypeMap;
    }

    public Map<Class, Class> getDaoInterfaceAndClassMap() {
        return daoInterfaceAndClassMap;
    }
}
