package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.Count;
import com.baizhi.entity.ModelByGender;
import com.baizhi.entity.User;
import com.baizhi.util.MD5Utils;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao dao;

    @Override
    //查询每个月注册的人数
    public Map<String ,Object> count(String gender) {
        HashMap<String, Object> map  = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        List<Count> count = dao.count();
        for (Count count1 : count) {
             list.add(count1.getMonth());
             list1.add(count1.getCount());
        }
        map.put("count",list);
        map.put("month",list1);



        List<ModelByGender> modelByGenders = dao.selectByGender("男");
        List<ModelByGender> modelByGenders1 = dao.selectByGender("女");
        map.put("name",modelByGenders);
        map.put("value",modelByGenders1);


        Gson gson = new Gson();
        String s = gson.toJson(map);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-2e977302132f49b0bc9c69ae2f9ba070");
        goEasy.publish("demo_channel",s);
        return  map;
    }

    //登录
    @Override
    public Map<String, Object> regist(User user) {
        HashMap<String , Object> map = new HashMap<>();
        User login = dao.login(user.getPhone());
        String salt = login.getSalt();
        String password1 = MD5Utils.getPassword(user.getPassword() + salt);
        if(login==null){
            map.put("code",-200);
            map.put("message","用户名不存在");
        }else if(password1.equals(login.getPassword())){
            map.put("userId",login.getId());
            map.put("phone",login.getPhone());
            map.put("password",login.getPassword());
            map.put("salt",login.getSalt());
            map.put("dharmaName",login.getDharmaName());
            map.put("province",login.getProvince());
            map.put("city",login.getCity());
            map.put("gender",login.getGender());
            map.put("personalSign",login.getPersonalSign());
            map.put("profile",login.getProfile());
            map.put("status",login.getStatus());
            map.put("registTime",login.getRegistTime());
        }else{
            map.put("code",300);
            map.put("message","密码错误");
        }
        return map;
    }
//添加
    @Override
    @Transactional
    public Map<String, Object> addUser(User user) {
        HashMap<String , Object> map = new HashMap<>();
        //判断手机号是否存在
        User login = dao.login(user.getPhone());
        if(login==null){
            user.setId(UUID.randomUUID().toString());
            String salt = MD5Utils.getSalt();
            user.setSalt(salt);
            String password = MD5Utils.getPassword(user.getPassword() + salt);
            user.setPassword(password);
            user.setStatus("正常");
            dao.insert(user);
            map.put("userId",user.getId());
            map.put("phone",user.getPhone());
            map.put("password",user.getPassword());
            map.put("salt",user.getSalt());
            map.put("dharmaName",user.getDharmaName());
            map.put("province",user.getProvince());
            map.put("city",user.getCity());
            map.put("gender",user.getGender());
            map.put("personalSign",user.getPersonalSign());
            map.put("profile",user.getProfile());
            map.put("status",user.getStatus());
            map.put("registTime",user.getRegistTime());
            count(user.getGender());
        }else{
            map.put("code",-400);
            map.put("message","手机号已存在");
        }
        return map;
    }













    @Override
    @Transactional
    public void add(User user) {
        String s = UUID.randomUUID().toString();
        user.setSalt("121221");
        user.setId(s);
        dao.insert(user);
    }



    @Override
    public UserPageDto queryAllByPage(Integer page, Integer rows) {
        UserPageDto userPageDto =new UserPageDto();
        userPageDto.setPage(page);//当前页
        int count =dao.selectCount();
        userPageDto.setTotal(count%rows==0? count/rows:count/rows+1);//总页数
        userPageDto.setRecords(count);//总行数
        userPageDto.setRows(dao.queryAllByPage(page,rows));
        return userPageDto;
    }

    @Override
    @Transactional
    public void update(User user) {
        if (user.getStatus().equals("正常")) {
            user.setStatus("冻结");
            dao.update(user);
        }else {
            user.setStatus("正常");
            dao.update(user);
        }
    }

    @Override
    public List<User> queryAll() {
        List<User> users = dao.queryAll();
        return users;
    }
}
