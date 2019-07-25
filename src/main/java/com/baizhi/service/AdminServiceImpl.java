package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao dao;

    @Override
    public List<Admin> QueyrAll() {
        List<Admin> admins = dao.selectAll();
        return admins;
    }

    @Override
    public Admin QueryOne(String username, String password) {
        Admin admin = dao.selectOne(username);
            if(admin==null)throw new RuntimeException("用户未注册");
            if(!password.equals(admin.getPassword()))throw new RuntimeException("账号密码不正确");

        return admin;
    }
}
