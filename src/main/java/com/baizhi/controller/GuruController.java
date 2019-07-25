package com.baizhi.controller;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.GuruPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("p4")
public class GuruController {
    @Autowired
    private GuruService service1;
    @RequestMapping("queryByPage")
    @ResponseBody
    public GuruPageDto queryByPage(Integer page, Integer rows){

        return service1.queryAllByPage(page,rows);
    }
    @RequestMapping("status")
    @ResponseBody
    public void status( Guru guru){
        System.out.println(guru+"3333");
        service1.update(guru);
    }
    @RequestMapping("edit")
    @ResponseBody
    public String edit(Guru guru, String oper) throws IOException {
        if("add".equals(oper)) {
            String s = service1.insert(guru);
            return s;
        }
        return null;

    }
@ResponseBody
@RequestMapping("upload")
    public void fileiupload(MultipartFile profile, String id, HttpServletRequest request , HttpServletResponse response){

    String filename = profile.getOriginalFilename();
    String path = request.getSession().getServletContext().getRealPath("GuruUpload");
    File file =new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        try {
            profile.transferTo(new File(path,filename));
            //修改数据库路径
            Guru guru = new Guru();
            guru.setId(id);
            guru.setProfile(filename);
            service1.modifyProfile(guru);
        } catch (IOException e) {
            e.printStackTrace();
      }
    }
}
