package com.yootk.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yootk.web.util.ParameterUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UploadServlet extends HttpServlet {
    private class Dept {
        private String dname;
        private String loc;
        private String photo;
        public Dept(String dname, String loc, String photo) {
            this.dname = dname;
            this.loc = loc;
            this.photo = photo;
        }
        public String getDname() {
            return dname;
        }
        public String getLoc() {
            return loc;
        }
        public String getPhoto() {
            return photo;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ParameterUtil pu = new ParameterUtil(request,"/upload/dept/") ; // 实例化上传工具类
        System.out.println("【UploadServlet】dname请求参数内容：" + pu.getParameter("dname"));
        System.out.println("【UploadServlet】loc请求参数内容：" + pu.getParameter("loc"));
        System.out.println("【UploadServlet】创建photo图片名称：" + pu.getParameter("photo"));
        Dept dept = new Dept(pu.getParameter("dname"), pu.getParameter("loc"), pu.getParameter("photo")) ;
        response.getWriter().println(JSON.toJSONString(dept));
    }
}