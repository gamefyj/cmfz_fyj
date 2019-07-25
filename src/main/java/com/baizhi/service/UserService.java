package com.baizhi.service;

import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.Count;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserPageDto queryAllByPage(Integer page,Integer rows);
    void update(User user);
    List<User> queryAll();
    void add(User user);
    Map<String,Object> addUser(User user);
    Map<String ,Object> regist(User user);
    Map<String ,Object> count(String gender);
}
