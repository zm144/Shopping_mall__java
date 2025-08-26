package com.lucys.web.servlet;

import com.lucys.domain.AdminUserPart;
import com.lucys.domain.User;
import com.lucys.service.UserService;
import com.lucys.utils.BeanFactory;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminUser")
public class AdminUserServlet extends BaseServlet {

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            UserService us = (UserService) BeanFactory.getBean("UserService");
            List<User> list = us.findList();
            request.setAttribute("list", list);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return "/admin/user/list.jsp";
    }
}