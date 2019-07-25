package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao dao;
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional
    public String insert(Chapter chapter) {
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        dao.insert(chapter);
        int i = dao.selectCount(chapter.getAlbumid());
        Album album = new Album();
        album.setId(chapter.getAlbumid());
        album.setCount(i);
        albumDao.update(album);
        return s;
    }

    @Override
    @Transactional
    public String update(Chapter chapter) {
        dao.update(chapter);
        return chapter.getId();
    }

    @Override
    @Transactional
    public void delete(String  id) {
    dao.delete(id);
    }



    @Override
    @Transactional
    public void modifyImgPath(Chapter chapter) {

        dao.updateImgPath(chapter);
    }

    public ChapterPageDto queryAllByPage(Integer page, Integer rows,String id) {

        //分页Dto属性赋值
        ChapterPageDto c = new ChapterPageDto();
        c.setPage(page);//当前页
        int count = dao.selectCount(id);
        c.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        c.setRecords(count);//总行数
        c.setRows(dao.queryAllByPage(page,rows,id));
        System.out.println("2222222222222"+c);
        return c;
    }
}
