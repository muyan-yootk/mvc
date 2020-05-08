package com.yootk.common.bean;
import com.yootk.common.annotation.Controller;
import com.yootk.common.annotation.RequestMapping;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
// 每一个Action类的Class都需要进行解析，而后返回解析的结果
public class ConfigAnnotationParseUtil { // 解析所有的配置注解
    private Map<String, ControllerRequestMapping> result = new HashMap<>() ;
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
}
