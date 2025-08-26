package com.lucys.web.servlet;
import com.lucys.constant.Constant;
import com.lucys.domain.User;
import com.lucys.service.UserService;
import com.lucys.service.impl.UserServiceImpl;
import com.lucys.utils.UUIDUtils;
import com.lucys.web.servlet.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/user")
public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
//跳转到注册界面
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/register.jsp";
    }
//    用户注册
    public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.封装对象
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());

            //1.1手动封装uid
            user.setUid(UUIDUtils.getId());

            //1.2手动封装激活状态 state
            user.setState(Constant.USER_IS_ACTIVE);

            //1.3手动封装激活码 code
            user.setCode(UUIDUtils.getCode());

            //2.调用service完成注册
            UserService us=new UserServiceImpl();
            us.regist(user);

            //3.页面转发 提示信息

        }catch (Exception e) {
            e.printStackTrace();
            //注册失败， 转发到 2.jsp
            return "/jsp/msg3.jsp";
        }

        return "/jsp/msg2.jsp";

    }
    /**
     * 跳转到登录页面
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/login.jsp";
    }
    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.获取用户名和密码
            String username = request.getParameter("username");
            String password	= request.getParameter("password");

            //2.调用service完成登录 返回值:user
            UserService us = new UserServiceImpl();
            User user=us.login(username,password);

            //3.判断user 根据结果生成提示
            if(user == null){
                //用户名和密码不匹配
                request.setAttribute("msg", "用户名和密码不匹配");;
                return "/jsp/login.jsp";
            }

            //登录成功 保存用户登录状态
            request.getSession().setAttribute("user", user);

            /////////////////记住用户名//////////////////////
            //判断是否勾选了记住用户名
            if(Constant.SAVE_NAME.equals(request.getParameter("savename"))){
                Cookie c = new Cookie("saveName", URLEncoder.encode(username, "utf-8"));

                c.setMaxAge(Integer.MAX_VALUE);
                c.setPath(request.getContextPath()+"/");

                response.addCookie(c);
            }
            ///////////////////////////////////////

            //跳转到 index.jsp
            response.sendRedirect(request.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "用户登录失败");
            return "/jsp/msg.jsp";
        }

        return null;
    }
    /**
     * 退出
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath());
        return null;
    }



}
