package com.lucys.dao.impl;

import com.lucys.dao.OrderDao;
import com.lucys.domain.Order;
import com.lucys.domain.OrderItem;
import com.lucys.domain.PageBean;
import com.lucys.domain.Product;
import com.lucys.utils.DataSourceUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    /**
     * 保存订单
     */
    public void save(Order o) throws Exception {
        QueryRunner qr = new QueryRunner();

        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(), sql, o.getOid(),o.getOrdertime(),o.getTotal(),
                o.getState(),o.getAddress(),o.getName(),
                o.getTelephone(),o.getUser().getUid());
    }



    @Override
    /**
     * 保存订单项
     */
    public void saveItem(OrderItem oi) throws Exception {
        QueryRunner qr = new QueryRunner();
		/*
		 * `itemid` varchar(32) NOT NULL,
				  `count` int(11) DEFAULT NULL,
				  `subtotal` double DEFAULT NULL,

				  `pid` varchar(32) DEFAULT NULL,
				  `oid` varchar(32) DEFAULT NULL,
		 */
        String sql = "insert into orderitem values(?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),
                oi.getProduct().getPid(),oi.getOrder().getOid());
    }
//获取我的订单总条数
    @Override
    public int getTotalRecord(String uid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from orders where uid=?";

        return ((Long)qr.query(sql,new ScalarHandler(),uid)).intValue();
    }
    //获取我的订单当前页数据

    @Override
    public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where uid=? order by ordertime desc limit ?,?";
        List<Order> list=qr.query(sql,new BeanListHandler<>(Order.class),uid,pb.getStartIndex(),pb.getPageSize());

        for(Order order: list){
            sql="SELECT * FROM orderitem oi,product p WHERE oi.pid=p.pid AND oi.oid=?";
            List<Map<String,Object>> maplist= qr.query(sql,new MapListHandler(),order.getOid());
            for(Map<String,Object> map: maplist){
               OrderItem oi=new OrderItem();
               BeanUtils.populate(oi,map);

                Product p=new Product();
                BeanUtils.populate(p,map);
                oi.setProduct(p);
                order.getItems().add(oi);

            }
        }
        return list;
    }

    @Override
    /**
     * 订单详情
     */
    public Order getById(String oid) throws Exception {
        //1.查询订单基本信息
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql ="select * from orders where oid = ?";
        Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);

        //2.查询订单项
        sql ="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
        //所有的订单项详情
        List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), oid);

        //遍历 获取每一个订单项详情 封装成orderitem 加入到当前订单的items中
        for (Map<String, Object> map : maplist) {
            //创建ordreitem
            OrderItem oi = new OrderItem();

            //封装
            BeanUtils.populate(oi, map);

            //手动封装product
            Product p = new Product();
            BeanUtils.populate(p, map);

            oi.setProduct(p);
            //将orderitem加入到订单的items中
            order.getItems().add(oi);
        }
        return order;
    }

    @Override
    public void update(Order order) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql="update orders set state = ?,address = ?,name =?,telephone = ? where oid = ?";
        qr.update(sql,order.getState(),order.getAddress(),order.getName(),
                order.getTelephone(),order.getOid());

    }

    @Override
    /**
     * 后台查询订单列表
     */
    public List<Order> findAllByState(String state) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders ";

        //判断state是否为空
        if(null==state || state.trim().length()==0){
            sql +=" order by ordertime desc";
            return qr.query(sql, new BeanListHandler<>(Order.class));
        }

        sql += " where state = ? order by ordertime desc";
        return qr.query(sql, new BeanListHandler<>(Order.class),state);
    }


}
