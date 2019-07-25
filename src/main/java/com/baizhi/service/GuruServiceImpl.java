package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.GuruDao;
import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.GuruPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class GuruServiceImpl implements GuruService {

    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional
    public String insert(Guru guru) {
        String s = UUID.randomUUID().toString();
        guru.setId(s);
        guruDao.insert(guru);
        return s;
    }

    @Override
    @Transactional
    public String update(Guru guru) {
            if(guru.getStatus().equals("正常")){
                guru.setStatus("冻结");
                guruDao.update(guru);
            }else {
                guru.setStatus("正常");
                guruDao.update(guru);
            }
        return guru.getId();
}


    @Override
    public void modifyProfile(Guru guru) {
        guruDao.updateProfile(guru);
    }

    @Override
    public GuruPageDto queryAllByPage(Integer page, Integer rows) {

        //分页Dto属性赋值
        GuruPageDto c = new GuruPageDto();
        c.setPage(page);//当前页
        int count = guruDao.selectCount();
        c.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        c.setRecords(count);//总行数
        c.setRows(guruDao.queryAllByPage(page,rows));
        System.out.println("111111111"+c);
        return c;
    }
}
