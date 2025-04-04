package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.dto.UserRegisterRequest;

public interface UserService {
    Integer register(UserRegisterRequest registerRequest);
    User getUserById(Integer userId);
}
