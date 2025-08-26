package com.lucys.web.servlet;

import com.lucys.domain.Category;
import com.lucys.service.CategoryService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.UUIDUtils;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/adminCategory")
public class AdminCategoryServlet extends BaseServlet {

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
            List<Category> list = cs.findList();
            request.setAttribute("list", list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/admin/category/list.jsp";
    }
    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/admin/category/add.jsp";
    }


    public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            Category c = new Category();
            c.setCid(UUIDUtils.getId());
            c.setCname(request.getParameter("cname"));
            CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
            cs.save(c);
            response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return null;
    }
}