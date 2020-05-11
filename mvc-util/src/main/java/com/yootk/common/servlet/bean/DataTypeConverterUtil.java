package com.yootk.common.servlet.bean;

import com.yootk.common.util.StringUtil;
import com.yootk.common.util.WebObjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DataTypeConverterUtil { // 进行字符串类型的转换处理
    /**
     * 进行参数的接收以及类型转换的处理
     * @param paramName 参数名称
     * @param type 转换的处理类型
     * @return 将所有的数据以Object形式返回
     */
    public static Object convert(String paramName, Class<?> type) {
        String value = WebObjectUtil.getParameterUtil().getParameter(paramName) ; // 接收请求参数
        if (String.class.equals(type)) {    // 当前参数的类型为String
            return value ;
        } else if (long.class.equals(type) || Long.class.equals(type)) {    // 长整型
            try {
                return Long.parseLong(value);
            } catch (Exception e) {
                return null ;
            }
        } else if (int.class.equals(type) || Integer.class.equals(type)) {  // 整型
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                return null ;
            }
        } else if (double.class.equals(type) || Double.class.equals(type)) {  // 整型
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
                return null ;
            }
        } else if (boolean.class.equals(type) || Boolean.class.equals(type)) {  // 整型
            try {
                return Boolean.parseBoolean(value);
            } catch (Exception e) {
                return null ;
            }
        } else if (MultipartFile.class.equals(type)) { // 现在的类型为上传文件
            try {
                System.err.println(WebObjectUtil.getParameterUtil().getAllUploadFile());
                return WebObjectUtil.getParameterUtil().getAllUploadFile().get(paramName).get(0) ;
            } catch (Exception e) {
                return null ;
            }
        } else {    // 所判断的类型并不属于常用的基本类型
            Object vo = null; // 需要进行对象的实例化
            try {
                vo = type.getConstructor().newInstance(); // 调用无参进行对象实例化
                setObjectFieldValue(vo); // 进行类中属性内容的配置
                return vo ; // 返回VO对象
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null ;
    }
    private static void setObjectFieldValue(Object obj) {
        Field fields [] = obj.getClass().getDeclaredFields() ; // 获取全部成员对象
        for (Field field : fields) {    // 迭代每一个成员属性
            try {
                Method method = obj.getClass().getDeclaredMethod("set" + StringUtil.initcap(field.getName()), field.getType());
                method.invoke(obj, convert(field.getName(), field.getType()));
            } catch (Exception e) {}
        }
    }
}
