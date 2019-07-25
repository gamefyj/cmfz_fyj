package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dto.AlbumPageDto;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao dao;

    @Override
    @Transactional
    public String insert(Album e) {
        String s = UUID.randomUUID().toString();
        e.setId(s);
        dao.insert(e);
        return s;
    }

    @Override
    @Transactional
    public String update(Album e) {
        dao.update(e);
        return e.getId();
    }

    @Override
    @Transactional
    public void delete(String  id) {
    dao.delete(id);
    }



    @Override
    public void modifyImgPath(Album album) {

        dao.updateImgPath(album);
    }

    @Override
    public AlbumPageDto queryAllByPage(Integer page, Integer rows) {

        //分页Dto属性赋值
        AlbumPageDto c = new AlbumPageDto();
        c.setPage(page);//当前页
        int count = dao.selectCount();
        c.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        c.setRecords(count);//总行数
        c.setRows(dao.queryAllByPage(page,rows));
        System.out.println("111111111"+c);
        return c;
    }
}
