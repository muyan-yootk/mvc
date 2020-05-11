package com.yootk.common.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebObjectUtil {
    public static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<ParameterUtil> parameterUtilThreadLocal = new ThreadLocal<>() ;

    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    public static ServletContext getApplication() {
        return requestThreadLocal.get().getServletContext();
    }
    public static ParameterUtil getParameterUtil() {
        return parameterUtilThreadLocal.get() ;
    }
}
