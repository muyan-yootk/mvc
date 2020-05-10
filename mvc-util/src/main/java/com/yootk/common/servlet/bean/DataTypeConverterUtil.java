package com.yootk.common.servlet.bean;

import com.yootk.common.util.WebObjectUtil;

public class DataTypeConverterUtil { // 进行字符串类型的转换处理
    /**
     * 进行参数的接收以及类型转换的处理
     * @param paramName 参数名称
     * @param type 转换的处理类型
     * @return 将所有的数据以Object形式返回
     */
    public static Object convert(String paramName, Class<?> type) {
        String value = WebObjectUtil.getRequest().getParameter(paramName) ; // 接收请求参数
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
        }
        return null ;
    }
}
