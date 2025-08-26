package com.lucys.web.servlet;

import com.lucys.domain.Product;
import com.lucys.service.ProductService;
import com.lucys.service.impl.ProductServiceImpl;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

//首页
@WebServlet("/index")
public class IndexServlet extends BaseServlet {
    @Override
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.调用productservice查询最新商品 和 热门商品
            ProductService ps = new ProductServiceImpl();
            List<Product> hotList=ps.findHot();
            List<Product> newList=ps.findNew();

            //2.将两个list都放入request域中
            request.setAttribute("hList", hotList);
            request.setAttribute("nList", newList);
        } catch (Exception e) {
        }

        return "/jsp/index.jsp";
    }
}