package com.lucys.service;

import com.lucys.domain.AdminUserPart;
import com.lucys.domain.User;

import javax.jws.soap.SOAPBinding;
import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void regist(User user) throws Exception;

    User login(String username, String password) throws Exception;

    List<User> findList() throws Exception;
}
