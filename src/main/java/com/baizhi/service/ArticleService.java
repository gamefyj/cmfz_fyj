package com.baizhi.service;

import com.baizhi.dto.ArticlePageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;

public interface ArticleService {
    String insert(Article article);
    String update(Article article);
    void delete(String id);
    ArticlePageDto queryAllByPage(Integer page, Integer rows, String id);
    Article queryOne(String id);
}
