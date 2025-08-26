package com.lucys.dao;

import com.lucys.domain.AdminUserPart;
import com.lucys.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void save(User user) throws SQLException;
    void update(User user) throws SQLException;

    User getByUsernameAndPwd(String username, String password) throws Exception;

    List<User> findAll() throws Exception;
}
