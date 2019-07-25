package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao{
    void insert(Chapter chapter);
    void update(Chapter chapter);
    void delete(String id);
    int selectCount(@Param("albumid") String albumid);
    List<Chapter> queryAllByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize,@Param("id") String id);
    void updateImgPath(Chapter chapter);
}
