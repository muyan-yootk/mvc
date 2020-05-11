package com.yootk.common.util;

import java.util.ResourceBundle;

public class ResourceLoader {
    private final static ResourceBundle VALIDATION_RESOURCE = ResourceBundle.getBundle("com.yootk.resource.Validation") ;
    private final static ResourceBundle ERROR_PAGE_RESOURCE = ResourceBundle.getBundle("com.yootk.resource.ErrorPage") ;

    /**
     * 根据指定的key获取对应的规则信息
     * @param key 规则的key
     * @return 如果key存在则返回具体的字符串，如果key不存在，则直接返回null
     */
    public static String getValidationRule(String key) {
        try {
            return VALIDATION_RESOURCE.getString(key);
        } catch (Exception e) {
            return null ;
        }
    }
    public static String getErrorPage(String key) {
        try {
            return ERROR_PAGE_RESOURCE.getString(key);
        } catch (Exception e) {
            try {
                return ERROR_PAGE_RESOURCE.getString("common.error.page");
            } catch (Exception e1) {
                return null ;
            }
        }
    }
}
