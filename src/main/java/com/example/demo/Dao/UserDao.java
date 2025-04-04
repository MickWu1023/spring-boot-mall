package com.example.demo.Dao;

import com.example.demo.Model.User;
import com.example.demo.dto.UserRegisterRequest;

public interface UserDao {
    Integer createUser(UserRegisterRequest registerRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
