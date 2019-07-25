package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    void insert(Album album);
    void update(Album album);
    void delete(String id);
    int selectCount();
    List<Album> queryAllByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    void updateImgPath(Album album);
}
