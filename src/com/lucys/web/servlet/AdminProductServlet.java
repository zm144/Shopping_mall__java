package com.lucys.web.servlet;

import com.lucys.domain.Product;
import com.lucys.service.CategoryService;
import com.lucys.service.ProductService;
import com.lucys.utils.BeanFactory;
import com.lucys.web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminProduct")
public class AdminProductServlet extends BaseServlet {
    private static final long serialVersionUID=1L;

    public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");

            request.setAttribute("list", cs.findList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "/admin/product/add.jsp";
    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
            List<Product> list = ps.findAll();


            request.setAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return "/admin/product/list.jsp";
    }
}
