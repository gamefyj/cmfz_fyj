package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselDao {
    void insert(Carousel c);
    void update(Carousel c);
    void delete(String id);
    Carousel selectByID(String id);
    int selectCount();
    List<Carousel> queryAllByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    void updateImgPath(Carousel carousel);
}
