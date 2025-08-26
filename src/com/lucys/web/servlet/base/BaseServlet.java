package com.lucys.web.servlet.base;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.获取方法名称
            String mName = request.getParameter("method");

            //1.1判断 参数是否为空  若为空,执行默认的方法
            if(mName == null || mName.trim().length()==0){
                mName = "index";
            }

            //2.获取方法对象
            Method method = this.getClass().getMethod(mName, HttpServletRequest.class,HttpServletResponse.class);

            //3.让方法执行,接受返回值
            String path=(String) method.invoke(this, request,response);

            //4.判断返回值是否为空 若不为空统一处理请求转发
            if(path != null){
                request.getRequestDispatcher(path).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    public String index(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("亲,不要捣乱,写完啊！");
        return null;
    }

}

