package com.yootk.common.servlet;

import com.yootk.common.bean.ScannerPackageUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    }
}
