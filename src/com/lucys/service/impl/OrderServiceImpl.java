package com.lucys.service.impl;

import com.lucys.dao.OrderDao;
import com.lucys.domain.Order;
import com.lucys.domain.OrderItem;
import com.lucys.domain.PageBean;
import com.lucys.service.OrderService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.DataSourceUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Override
    /**
     * 保存订单
     */

    public void save(Order order) throws Exception {
        try {
            //获取dao
            OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
            //1.开启事务
            DataSourceUtils.startTransaction();

            //2.向orders表中插入一条
            od.save(order);

            //3.向orderitem中插入n条
            for (OrderItem oi : order.getItems()) {
                od.saveItem(oi);
            }

            //4.事务控制
            DataSourceUtils.commitAndClose();
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.rollbackAndClose();
            throw e;
        }


    }

    //我的订单
    @Override
    public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception {

        OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
        PageBean<Order> pb=new PageBean<>(pageNumber,pageSize);


        int totalRecord=od.getTotalRecord(uid);
        pb.setTotalRecord(totalRecord);
        List<Order> data=od.findMyOrdersByPage(pb,uid);
pb.setData(data);


        return pb;
    }

    @Override
    /**
     * 订单详情
     */
    public Order getById(String oid) throws Exception {
        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        return od.getById(oid);
    }

    @Override
    /**
     * 修改订单
     */
    public void update(Order order) throws Exception {
        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        od.update(order);
    }

    @Override
    public List<Order> findAllByState(String state) throws Exception {

        OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
        return od.findAllByState(state);
    }

}
