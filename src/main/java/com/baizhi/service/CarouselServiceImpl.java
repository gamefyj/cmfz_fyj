package com.baizhi.service;

import com.baizhi.dao.CarouselDao;
import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselDao dao;

    @Override
    @Transactional
    public String insert(Carousel e) {
        String s = UUID.randomUUID().toString();
        e.setId(s);
        dao.insert(e);
        return s;
    }

    @Override
    @Transactional
    public String update(Carousel e) {
        dao.update(e);
        return e.getId();
    }

    @Override
    @Transactional
    public void delete(String  id) {
    dao.delete(id);
    }

    @Override
    public Carousel queryOne(String  id) {
        Carousel carousel = dao.selectByID(id);
        return carousel;
    }

    @Override
    public void modifyImgPath(Carousel carousel) {
        dao.updateImgPath(carousel);
    }

    @Override
    public CarouselPageDto queryAllByPage(Integer page, Integer rows) {

        //分页Dto属性赋值
        CarouselPageDto c = new CarouselPageDto();
        c.setPage(page);//当前页
        int count = dao.selectCount();
        c.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        c.setRecords(count);//总行数
        c.setRows(dao.queryAllByPage(page,rows));
        return c;
    }
}
