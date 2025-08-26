package com.lucys.service.impl;

import com.lucys.dao.ProductDao;
import com.lucys.dao.impl.ProductDaoImpl;
import com.lucys.domain.PageBean;
import com.lucys.domain.Product;
import com.lucys.service.ProductService;
import com.lucys.utils.BeanFactory;

import java.util.Collections;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> findHot() throws Exception {
        ProductDao pd= new ProductDaoImpl();
        return pd.findHot();

    }

    @Override
    public List<Product> findNew() throws Exception {
        ProductDao pd= (ProductDao) BeanFactory.getBean("ProductDao");
        return pd.findNew();

    }

    @Override
    /**
     * 单个商品详情
     */
    public Product getById(String pid) throws Exception {
        ProductDao pd=new ProductDaoImpl();

        return pd.getById(pid);
    }

    @Override
    /**
     * 分页展示分类商品
     */
    public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
        ProductDao pDao= new ProductDaoImpl();
        //1.创建pagebean
        PageBean<Product> pb = new PageBean<>(pageNumber, pageSize);

        //2.设置当前页数据
        List<Product> data = pDao.findByPage(pb,cid);
        pb.setData(data);

        //3.设置总记录数
        int totalRecord = pDao.getTotalRecord(cid);
        pb.setTotalRecord(totalRecord);

        return pb;
    }

    @Override
    public List<Product> findAll() throws Exception {
        ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
        return pDao.findAll();
    }

    @Override
    public void save(Product p) throws Exception {
        ProductDao pDao = (ProductDao) BeanFactory.getBean(("ProductDao"));
        pDao.save(p);
    }


}
