package com.lucys.service;

import com.lucys.domain.Category;

import java.util.List;

public interface CategoryService {
    String findAll() throws Exception;

    List<Category> findList() throws Exception;

    void save(Category c) throws Exception;
}
