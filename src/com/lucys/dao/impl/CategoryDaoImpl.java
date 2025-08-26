package com.lucys.dao.impl;

import com.lucys.dao.CategoryDao;
import com.lucys.domain.Category;
import com.lucys.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.Collections;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    /**
     * 查询所有分类
     */
    public List<Category> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        return qr.query(sql, new BeanListHandler<>(Category.class));
    }

    @Override
    public void save(Category c) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into category values(?,?)";
        qr.update(sql,c.getCid(),c.getCname());
    }
}
