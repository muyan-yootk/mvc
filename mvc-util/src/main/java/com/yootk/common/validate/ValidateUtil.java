package com.yootk.common.validate;

import com.yootk.common.bean.ControllerRequestMapping;
import com.yootk.common.util.ResourceLoader;
import com.yootk.common.util.WebObjectUtil;

import java.util.HashMap;
import java.util.Map;

public class ValidateUtil {
    private ControllerRequestMapping mapping ;
    // 保存违反了验证规则的错误信息，key = 参数名称、value = 错误信息；
    private Map<String, String> errors ;
    public ValidateUtil(ControllerRequestMapping mapping) {
        this.errors = new HashMap<>() ; // 每一次验证都有一个独立的Map集合
        this.mapping = mapping ;
    }
    public boolean validate() { // 实现具体的验证操作
        // 根据执行的Action路径获取当前路径对应操作方法的验证规则
        String validationKey = mapping.getActionClazz().getName() + "." + mapping.getActionMethod().getName() ;
        String validationRule = ResourceLoader.getValidationRule(validationKey) ;// 获取规则
        if (validationRule == null) {   // 没有配置规则 = 不验证
            return true ;
        } else {    // 如果此时存在有规则，那么就需要进行规则的验证
            String rules [] = validationRule.split("\\|") ; // 拆分规则
            for (int x = 0 ; x < rules.length ; x ++) { // 获取所有的规则信息
                String temp [] = rules[x].split(":") ; // 规则拆分
                // 根据参数的名称获取对应的提交参数的内容
                String value = WebObjectUtil.getRequest().getParameter(temp[0]) ;
                switch (temp[1]) {  // JDK 1.7之后，switch开始支持字符串判断
                    case "int": {
                        if (!this.isInt(value)) {    // 是否是整数
                            this.errors.put(temp[0], "该参数的内容必须设置为整数。") ;
                        }
                        break;
                    }
                    case "long": {
                        if (!this.isLong(value)) {    // 是否是整数
                            this.errors.put(temp[0], "该参数的内容必须设置为整数。") ;
                        }
                        break;
                    }
                    case "double": {
                        if (!this.isDouble(value)) {    // 是否是整数
                            this.errors.put(temp[0], "该参数的内容必须设置为小数。") ;
                        }
                        break;
                    }
                    case "string": {
                        if (!this.isString(value)) {    // 是否是整数
                            this.errors.put(temp[0], "该参数的内容不允许为空。") ;
                        }
                        break;
                    }
                }
            }
        }
        return this.errors.size() == 0 ; // 有错误信息
    }
    public Map<String, String> getErrors() {    // 返回全部的错误内容
        return errors;
    }
    /**
     * 判断传入的字符串的内容是否为null，如果不为空返回true，表示验证通过
     * @param str 要验证的数据
     * @return 通过验证返回true，否则返回false
     */
    public boolean isString(String str) {
        if (str == null || "".equals(str)) {
            return false ;
        }
        return true ;
    }
    public boolean isLong(String str) {
        if (this.isString(str)) {   // 内容不为空
            if (str.matches("\\d+")) {
                return true ;
            }
        }
        return false ;
    }
    public boolean isInt(String str) {
        if (this.isString(str)) {   // 内容不为空
            if (str.matches("\\d+")) {
                return true ;
            }
        }
        return false ;
    }
    public boolean isDouble(String str) {
        if (this.isString(str)) {   // 内容不为空
            if (str.matches("\\d+(\\.\\d+)?")) {
                return true ;
            }
        }
        return false ;
    }
}
