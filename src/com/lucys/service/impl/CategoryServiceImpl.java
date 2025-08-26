package com.lucys.service.impl;

import com.lucys.dao.CategoryDao;
import com.lucys.dao.impl.CategoryDaoImpl;
import com.lucys.domain.Category;
import com.lucys.service.CategoryService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.JsonUtil;

import java.util.Collections;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    /**
     * 查询所有分类
     */
    public String findAll() throws Exception {
        //1.调用dao 查询所有分类
       List<Category> list = findList();

        //2.将list转换成json字符串
        if(list!=null && list.size()>0){
            return JsonUtil.list2json(list);
        }
        return null;
    }

    @Override
    public List<Category> findList() throws Exception {
        CategoryDao cd= (CategoryDao) BeanFactory.getBean("CategoryDao");

        return cd.findAll();
    }

    @Override
    public void save(Category c) throws Exception {
            CategoryDao cd= (CategoryDao) BeanFactory.getBean("CategoryDao");
            cd.save(c);
    }

}
