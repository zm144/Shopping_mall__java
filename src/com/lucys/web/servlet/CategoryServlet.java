package com.lucys.web.servlet;

import com.lucys.service.CategoryService;
import com.lucys.service.impl.CategoryServiceImpl;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //0.设置响应编码
            response.setContentType("text/html;charset=utf-8");

            //1.调用service,查询所有分类,返回值 json字符串
            CategoryService cs = new CategoryServiceImpl();
            //从mysql获取列表
            String value = cs.findAll();

            //从redis中获取列表
            //String value = cs.findAllFromRedis();
            //2.将字符串写回浏览器
            response.getWriter().println(value);
        } catch (Exception e) {
        }
        return null;
    }

}