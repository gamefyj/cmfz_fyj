package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import com.baizhi.entity.Guru;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuruDao {
    void insert(Guru guru);
    void update(Guru guru);
    void delete(String id);
    int selectCount();
    List<Guru> queryAllByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    void updateProfile (Guru guru);
}
