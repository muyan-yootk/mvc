package com.yootk.web.servlet;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeptServlet extends HttpServlet {
    private class Dept {
        private Long deptno ;
        private String dname ;
        private String loc ;
        public Dept(Long deptno, String dname, String loc) {
            this.deptno = deptno;
            this.dname = dname;
            this.loc = loc;
        }
        public Long getDeptno() {
            return deptno;
        }
        public String getDname() {
            return dname;
        }
        public String getLoc() {
            return loc;
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.err.println("【参数接收】title = " + request.getParameter("title"));
        System.err.println("【参数接收】message = " + request.getParameter("message"));
        List<Dept> allDepts = new ArrayList<>() ;
        allDepts.add(new Dept(10L, "技术部", "北京"));
        allDepts.add(new Dept(20L, "销售部", "上海"));
        allDepts.add(new Dept(30L, "市场部", "广州"));
        response.getWriter().println(JSON.toJSONString(allDepts));
    }
}

