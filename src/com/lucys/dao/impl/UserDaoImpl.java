package com.lucys.dao.impl;
import com.lucys.dao.UserDao;
import com.lucys.domain.AdminUserPart;
import com.lucys.domain.User;
import com.lucys.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public void save(User user) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
        qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
                user.getName(),user.getEmail(),user.getTelephone(),
                user.getBirthday(),user.getSex(),user.getState(),
                user.getCode());


    }

    @Override
    public void update(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*
		 *  `uid` varchar(32) NOT NULL,
			  `username` varchar(20) DEFAULT NULL,
			  `password` varchar(20) DEFAULT NULL,

			  `name` varchar(20) DEFAULT NULL,
			  `email` varchar(30) DEFAULT NULL,
			  `telephone` varchar(20) DEFAULT NULL,

			  `birthday` date DEFAULT NULL,
			  `sex` varchar(10) DEFAULT NULL,
			  `state` int(11) DEFAULT NULL,

			  `code` varchar(64) DEFAULT NULL,
		 */
        String sql="update user set password = ?,sex = ?,state = ?,code = ? where uid = ?";
        qr.update(sql, user.getPassword(),user.getSex(),user.getState(),user.getCode(),user.getUid());

    }

    @Override
    /**
     * 用户登录
     */
    public User getByUsernameAndPwd(String username, String password) throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ? limit 1";
        return qr.query(sql, new BeanHandler<>(User.class), username,password);
    }

    @Override
    public List<User> findAll() throws Exception {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user";
        return qr.query(sql, new BeanListHandler<User>(User.class));
    }

}
