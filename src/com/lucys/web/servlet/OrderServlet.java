package com.lucys.web.servlet;

import com.lucys.constant.Constant;
import com.lucys.domain.*;
import com.lucys.service.OrderService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.PaymentUtil;
import com.lucys.utils.UUIDUtils;
import com.lucys.web.servlet.base.BaseServlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@WebServlet("/order")
public class OrderServlet extends BaseServlet {



    /**
     * 在线支付
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取收获信息 获取oid 获取银行

        //2.调用service获取订单 修改收获人信息  更新订单

        //3.拼接给第三方的url

        //4.重定向


        try {
            //接受参数
            String address=request.getParameter("address");
            String name=request.getParameter("name");
            String telephone=request.getParameter("telephone");
            String oid=request.getParameter("oid");


            //通过id获取order
            OrderService s=(OrderService) BeanFactory.getBean("OrderService");
            Order order = s.getById(oid);

            order.setAddress(address);
            order.setName(name);
            order.setTelephone(telephone);

            //更新order
            s.update(order);


            // 组织发送支付公司需要哪些数据
            String pd_FrpId = request.getParameter("pd_FrpId");
            String p0_Cmd = "Buy";
            String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
            String p2_Order = oid;
            String p3_Amt = "0.01";
            String p4_Cur = "CNY";
            String p5_Pid = "";
            String p6_Pcat = "";
            String p7_Pdesc = "";
            // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
            // 第三方支付可以访问网址
            String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
            String p9_SAF = "";
            String pa_MP = "";
            String pr_NeedResponse = "1";
            // 加密hmac 需要密钥
            String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
            String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                    p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                    pd_FrpId, pr_NeedResponse, keyValue);
            // 自己对上面数据进行加密 --- 比较支付公司发过来hamc
            boolean isValid = true;
            if (isValid) {
                // 响应数据有效
                    // 浏览器重定向
                    System.out.println("111");
                    request.setAttribute("msg", "您的订单号为:"+oid+",金额为:"+order.getTotal()+"已经支付成功,等待发货~~");
                //修改订单状态
                order.setState(Constant.ORDER_YIFUKUAN);
                s.update(order);

            } else {
                // 数据无效
                System.out.println("数据被篡改！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "支付失败");
        }


        return "/jsp/msg.jsp";
    }



    /**
     * 获取订单详情
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.获取oid
            String oid = request.getParameter("oid");

            //2.调用service 查询单个订单
            OrderService os = (OrderService) BeanFactory.getBean("OrderService");
            Order order = os.getById(oid);

            //3.请求转发
            request.setAttribute("bean",order);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "查询订单详情失败");
            return "/jsp/msg.jsp";
        }
        return "/jsp/order_info.jsp";
    }


    public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {


          int pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
          int pageSize=3;
          User user= (User) request.getSession().getAttribute("user");
          if(user==null){
              request.setAttribute("msg","请先登录");
             return "/jsp/msg.jsp";
          }
            OrderService os= (OrderService) BeanFactory.getBean("OrderService");
            PageBean<Order> bean = os.findMyOrdersByPage(pageNumber,pageSize,user.getUid());
            request.setAttribute("pb",bean);
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("msg","获取我的订单失败");
            return "/jsp/msg.jsp";
        }

          return "/jsp/order_list.jsp";
    }

    /**
     * 保存订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //-1.从session中获取user
            User user=(User) request.getSession().getAttribute("user");
            if(user == null){
                //未登录 提示
                request.setAttribute("msg", "请先登录!");
                return "/jsp/msg.jsp";
            }

            //0.获取购物车
            Cart cart=(Cart) request.getSession().getAttribute("cart");

            //1.封装订单对象
            //1.1创建对象
            Order order = new Order();

            //1.2设置oid
            order.setOid(UUIDUtils.getId());

            //1.3设置ordertime
            order.setOrdertime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            //1.4设置total 购物车中
            order.setTotal(cart.getTotal());

            //1.5设置state
            order.setState(Constant.ORDER_WEIFUKUAN);

            //1.6设置user
            order.setUser(user);

            //1.7设置items(订单项列表) 遍历购物项列表
            for (CartItem ci : cart.getCartItems()) {
                //1.7.1封装成orderitem
                //a.创建orderitem
                OrderItem oi = new OrderItem();

                //b.设置itemid uuid
                oi.setItemid(UUIDUtils.getId());

                //c.设置count 从ci中获取
                oi.setCount(ci.getCount());

                //d.设置subtotal 从ci中获取
                oi.setSubtotal(ci.getSubtotal());

                //e.设置product 从ci中获取
                oi.setProduct(ci.getProduct());

                //f.设置order
                oi.setOrder(order);

                //1.7.2 将orderitem加入order 的items中
                order.getItems().add(oi);
            }


            //2.调用orderservice完成保存操作
            OrderService os = (OrderService) BeanFactory.getBean("OrderService");
            os.save(order);
            //清空购物车
            cart.clearCart();

            //3.请求转发到 order_info.jsp
            request.setAttribute("bean", order);
        } catch (Exception e) {
        }
        return "/jsp/order_info.jsp";
    }


}