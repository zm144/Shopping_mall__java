package com.lucys.dao;

import com.lucys.domain.Order;
import com.lucys.domain.OrderItem;
import com.lucys.domain.PageBean;

import java.util.List;

public interface OrderDao {
    void save(Order order) throws Exception;

    void saveItem(OrderItem oi) throws Exception;

    int getTotalRecord(String uid) throws Exception;

    List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception;

    Order getById(String oid) throws Exception;

    void update(Order order) throws Exception;

    List<Order> findAllByState(String state) throws Exception;
}
