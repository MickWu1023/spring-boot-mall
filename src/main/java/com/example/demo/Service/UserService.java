package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    Integer register(UserRegisterRequest registerRequest);
    User getUserById(Integer userId);
    User login(UserLoginRequest userLoginRequest);
}
