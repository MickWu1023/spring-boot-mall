package com.example.demo.Dao.impl;

import com.example.demo.Dao.UserDao;
import com.example.demo.Model.User;
import com.example.demo.Rowmapper.UserRowmapper;
import com.example.demo.dto.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserByEmail(String email) {
        String sql = "select user_id,email,password,created_date,last_modified_date" +
                " from user where email=:email";
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowmapper());
        if(userList.size()>0){
            return userList.get(0);
        }else {
        return null;
        }
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "select user_id,email,password,created_date,last_modified_date" +
                " from user where user_id=:userId";
        Map<String ,Object>  map = new HashMap<>();
        map.put("userId",userId);
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowmapper());
        if(userList.size()>0){
            return userList.get(0);
        }else
            return null;

    }
    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "Insert into user(email,password,created_date,last_modified_date) values (:email,:password,:createdDate,:lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date date = new Date();
        map.put("createdDate",date);
        map.put("lastModifiedDate", date);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        Integer userId = keyHolder.getKey().intValue();
        return userId;

    }


}
