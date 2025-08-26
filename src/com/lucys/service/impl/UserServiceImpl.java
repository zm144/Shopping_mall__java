package com.lucys.service.impl;

import com.lucys.constant.Constant;
import com.lucys.dao.UserDao;
import com.lucys.dao.impl.UserDaoImpl;
import com.lucys.domain.AdminUserPart;
import com.lucys.domain.User;
import com.lucys.service.UserService;
import com.lucys.utils.BeanFactory;
import com.lucys.utils.MailUtils;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class UserServiceImpl implements UserService {
    @Override
//    注册
    public void regist(User user) throws Exception {
        //1.调用dao完成注册
        UserDao ud= (UserDao) BeanFactory.getBean("UserDao");
        ud.save(user);
        user.setCode(null);
        ud.update(user);
    }

    @Override
    /**
     * 用户登录
     */
    public User login(String username, String password) throws Exception {
        UserDao ud= (UserDao) BeanFactory.getBean("UserDao");

        return ud.getByUsernameAndPwd(username,password);
    }

    @Override
    public List<User> findList() throws Exception {
        UserDao ud= (UserDao) BeanFactory.getBean("UserDao");
        List<User> list = ud.findAll();

        return list;
    }



}
