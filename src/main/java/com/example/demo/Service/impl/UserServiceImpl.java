package com.example.demo.Service.impl;

import com.example.demo.Dao.UserDao;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserRegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl  implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest registerRequest) {
        //檢查註冊的email
        User user = userDao.getUserByEmail(registerRequest.getEmail());
        if(user!=null){
            log.warn("該email {} 已經被註冊" ,registerRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //創建帳號
        return userDao.createUser(registerRequest) ;
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
