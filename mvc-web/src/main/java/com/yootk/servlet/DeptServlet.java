package com.yootk.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeptServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status"); // 依据当前给定的status参数来判断执行的模式
        if ("list".equalsIgnoreCase(status)) {
            this.list(request, response);
        } else if ("add".equalsIgnoreCase(status)) {
            this.add(request, response);
        } else if ("edit".equalsIgnoreCase(status)) {
            this.edit(request, response);
        } else if ("remove".equalsIgnoreCase(status)) {
            this.remove(request, response);
        }
    }
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
}
