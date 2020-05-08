package com.yootk.common.servlet;

import com.yootk.common.bean.ControllerRequestMapping;
import com.yootk.common.bean.ScannerPackageUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
        // 以下可以根据用户的访问路径进行动态的信息获取
        String path = request.getServletPath().substring(0, request.getServletPath().lastIndexOf(".action")) ;
        ControllerRequestMapping mapping = ScannerPackageUtil.getActionMap().get(path) ; // 根据路径获取ControllerRequestMapping
        try {
            Object actionObject = mapping.getActionClazz().getDeclaredConstructor().newInstance() ; // 获取Action实例化对象
            mapping.getActionMethod().invoke(actionObject); // 方法中没有参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
