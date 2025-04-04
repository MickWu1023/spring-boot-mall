package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/users/register")
    public ResponseEntity<User> Register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId =  userService.register(userRegisterRequest);
        User user = userService.getUserById(userId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer userId) {
        User user =  userService.getUserById(userId);
                return user;
    }
}

