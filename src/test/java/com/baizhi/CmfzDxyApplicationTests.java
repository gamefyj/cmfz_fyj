package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzDxyApplicationTests {
@Autowired
    AdminService service;
    @Test
    public void contextLoads() {
        Admin admin = service.QueryOne("1", "2");
        System.out.println(admin);
    }


}
