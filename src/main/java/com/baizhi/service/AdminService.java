package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin>QueyrAll();
    Admin QueryOne(String username,String password);
}
