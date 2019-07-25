package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController  {
    @Autowired
    private UserService userService;
    //每个月注册人数
    @RequestMapping("count")
    public Map<String, Object> count(User user){
        Map<String, Object> map = userService.count(user.getGender());
        return map;
    }


//登录用户
    @RequestMapping("login")
    public Map<String, Object> login(User user){
        Map<String, Object> regist = userService.regist(user);
        return  regist;
    }

//注册用户
@RequestMapping("regist")
    public Map<String, Object> regist(User user){
    Map<String, Object> add = userService.addUser(user);
    return  add;
}

    @RequestMapping("queryByPage")
    public UserPageDto queryByPage(Integer page, Integer rows){

        return userService.queryAllByPage(page,rows);
    }
    @RequestMapping("update")
    public void update(User user){
        userService.update(user);
    }



    @RequestMapping("importFile")
    public void importFile(MultipartFile file, HttpSession session, HttpServletResponse response) throws Exception {
        List<User> users = userService.queryAll();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户表"),
                User.class, users);
        //响应头
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("用户表.xls","utf-8"));
        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }
    @RequestMapping("daoru")
    public String daoru(MultipartFile file)  {
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取第一个表
        Sheet sheet = workbook.getSheetAt(0);
        //获取一共有多少行
        int lastRowNum = sheet.getLastRowNum();
        //创建一个集合去接受
        List<User> list = new ArrayList<>();
        //遍历行数
        for (int i = 0; i < lastRowNum-1; i++) {
            User user = new User();
            Row row = sheet.getRow(i + 2);
            System.out.println(i+2);
            Cell cell = row.getCell(0);
            String id = cell.getStringCellValue();
            user.setId(id);
            Cell cell1 = row.getCell(1);
            user.setPhone(cell1.getStringCellValue());
            Cell cell2 = row.getCell(2);
            user.setPassword(cell2.getStringCellValue());
            Cell cell3 = row.getCell(3);
            user.setDharmaName(cell3.getStringCellValue());
            Cell cell4 = row.getCell(4);
            user.setProvince(cell4.getStringCellValue());
            Cell cell5 = row.getCell(5);
            user.setCity(cell5.getStringCellValue());
            Cell cell6 = row.getCell(6);
            user.setGender(cell6.getStringCellValue());
            Cell cell7 = row.getCell(7);
            user.setPersonalSign(cell7.getStringCellValue());
            Cell cell8 = row.getCell(8);
            user.setProfile(cell8.getStringCellValue());
            Cell cell9 = row.getCell(9);
            user.setStatus(cell9.getStringCellValue());
            Cell cell10 = row.getCell(10);
            user.setRegistTime(cell10.getDateCellValue());
            userService.add(user);
            list.add(user);
        }
        return "1";
    }
}