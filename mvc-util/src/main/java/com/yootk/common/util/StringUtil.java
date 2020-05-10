package com.yootk.common.util;

public class StringUtil {
    private StringUtil() {}
    public static String firstLower(String str) {
        if (str == null || "".equals(str)) { // 不进行处理
            return str;
        }
        if (str.length() == 1) {    // 字符串长度为1
            return str.toLowerCase(); // 一位长度的字符串直接转换即可
        }
        return str.substring(0,1).toLowerCase() + str.substring(1) ;
    }
}
