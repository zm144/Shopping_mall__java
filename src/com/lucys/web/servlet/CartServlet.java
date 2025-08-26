package com.lucys.web.servlet;

import com.lucys.domain.Cart;
import com.lucys.domain.CartItem;
import com.lucys.domain.Product;
import com.lucys.service.ProductService;
import com.lucys.utils.BeanFactory;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {
    /**
     * 加入购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.获取pid count
            String pid = request.getParameter("pid");
            int count = Integer.parseInt(request.getParameter("count"));

            //2.封装cartitem
            //调用service获取product
            ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
            Product product = ps.getById(pid);

            //创建cartitem
            CartItem cartItem = new CartItem(product, count);

            //3.将cartitem加入购物车
            //获取购物车
            Cart cart=getCart(request);

            cart.add2cart(cartItem);

            //4.重定向
            response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "加入购物车失败");
            return "/jsp/msg.jsp";
        }

        return null;
    }

    private Cart getCart(HttpServletRequest request) {
          Cart cart= (Cart) request.getSession().getAttribute("cart");
             if(cart==null){
                 cart = new Cart();
                 //将cart放入session中
                request.getSession().setAttribute("cart", cart);
              }
          return cart;
      }
    /**
     * 从购物车移除商品
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取商品的pid
        String pid = request.getParameter("pid");

        //2.获取购物车 执行移除
        getCart(request).removeFromCart(pid);

        //3.重定向
        response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
        return null;
    }

    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取购物车 执行清空操作
        getCart(request).clearCart();

        //2.重定向
        response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");

        return null;
    }


}