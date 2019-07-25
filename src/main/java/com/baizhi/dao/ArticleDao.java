package com.baizhi.dao;

import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    void insert(Article article);
    void update(Article article);
    void delete(String id);
    int selectCount(@Param("guruId") String guruId);
    List<Article> queryAllByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("id") String id);
    Article selectOne(@Param("id") String id);
}
