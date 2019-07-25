package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.dto.ArticlePageDto;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao dao;

    @Override
    public Article queryOne(String id) {
        Article article = dao.selectOne(id);

        return article;
    }

    @Override
    @Transactional
    public String insert(Article article) {
        String s = UUID.randomUUID().toString();
        article.setId(s);
        article.setPublishTime(new Date());
        dao.insert(article);
        return s;
    }

    @Override
    @Transactional
    public String update(Article article) {
        dao.update(article);
        return article.getId();
    }

    @Override
    @Transactional
    public void delete(String  id) {
    dao.delete(id);
    }

    public ArticlePageDto queryAllByPage(Integer page, Integer rows, String id) {

        //分页Dto属性赋值
        ArticlePageDto c = new ArticlePageDto();
        c.setPage(page);//当前页
        int count = dao.selectCount(id);
        c.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        c.setRecords(count);//总行数
        c.setRows(dao.queryAllByPage(page,rows,id));
        return c;
    }
}
