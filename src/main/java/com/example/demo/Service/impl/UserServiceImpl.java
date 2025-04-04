package com.example.demo.Service.impl;

import com.example.demo.Dao.UserDao;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest registerRequest) {
        return userDao.createUser(registerRequest) ;
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
