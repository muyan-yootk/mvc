package com.yootk.common.servlet;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yootk.common.bean.ControllerRequestMapping;
import com.yootk.common.bean.DependObject;
import com.yootk.common.bean.ModeAndView;
import com.yootk.common.bean.ScannerPackageUtil;
import com.yootk.common.servlet.bean.ActionParameterUtil;
import com.yootk.common.util.WebObjectUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 此servlet主要实现请求的接收以及Action分发处理，在整个的处理过程之中该Servlet不要直接通过注解配置
 * 因为这个类需要被其他的项目模块所引用，而每个模块的配置是不同的；
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        String scanPackages = config.getInitParameter("scanPackages") ; // 获取初始化配置参数
        ScannerPackageUtil.scannerHandle(this.getClass(), scanPackages); // 包扫描控制
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebObjectUtil.requestThreadLocal.set(request);
        WebObjectUtil.responseThreadLocal.set(response);
        // 以下可以根据用户的访问路径进行动态的信息获取
        String path = request.getServletPath().substring(0, request.getServletPath().lastIndexOf(".action")) ;
        ControllerRequestMapping mapping = ScannerPackageUtil.getActionMap().get(path) ; // 根据路径获取ControllerRequestMapping
        try {
            Object actionObject = mapping.getActionClazz().getDeclaredConstructor().newInstance() ; // 获取Action实例化对象
            DependObject dependObject = new DependObject(actionObject) ; // 由控制层调用依赖配置
            // 方法调用完成之后一般都会存在有返回值信息
            Object returnData = mapping.getActionMethod().invoke(actionObject, ActionParameterUtil.getMethodParameterValue(actionObject, mapping.getActionMethod())); // 方法中没有参数
            if (returnData != null) {   // 进行任何的页面跳转
                if (returnData.getClass().equals(String.class)) {   // 返回的类型是字符串
                    request.getRequestDispatcher(returnData.toString()).forward(request, response); // 跳转
                } else if (returnData.getClass().equals(ModeAndView.class)) {
                    ModeAndView mav = (ModeAndView) returnData ; // 强制转型
                    for (Map.Entry<String, Object> entry : mav.getAttributes().entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue()); // 属性配置
                    }
                    request.getRequestDispatcher(mav.getPath()).forward(request, response); // 跳转
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
