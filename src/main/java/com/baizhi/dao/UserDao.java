package com.baizhi.dao;

import com.baizhi.entity.Count;
import com.baizhi.entity.ModelByGender;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    void update(User user);
    List<User> queryAllByPage(@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);
    int selectCount();
    List<User> queryAll();
    void insert(User user);
    User login(@Param("phone") String phone);
    //统计注册月份
    List<Count> count();
    //统计地区男女注册人数
    List<ModelByGender> selectByGender(@Param("gender") String gender);
}
