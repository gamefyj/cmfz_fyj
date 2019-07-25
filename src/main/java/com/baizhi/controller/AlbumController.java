package com.baizhi.controller;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.CarouselPageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AdminService;
import com.baizhi.service.AlbumService;
import com.baizhi.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("p2")
public class AlbumController {
    @Autowired
    private AlbumService service1;
    @RequestMapping("queryByPage")
    @ResponseBody
    public AlbumPageDto queryByPage(Integer page, Integer rows){

        return service1.queryAllByPage(page,rows);
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Album album, String oper,String[] id) throws IOException {
        if("add".equals(oper)) {
            String s = service1.insert(album);
            return s;
        }else if("edit".equals(oper)) {
            String s = service1.update(album);
            return s;
        } else
            if(id!=null){
                for (String s : id) {
                    service1.delete(s);
                }
            }
        return null;

    }
@ResponseBody
@RequestMapping("upload")
    public void fileiupload(MultipartFile cover, String id, HttpServletRequest request , HttpServletResponse response){

    String filename = cover.getOriginalFilename();
    String path = request.getSession().getServletContext().getRealPath("upload");
    File file =new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        try {
            cover.transferTo(new File(path,filename));
            //修改数据库路径
            Album album = new Album();
            album.setId(id);
            album.setCover(filename);
            service1.modifyImgPath(album);
        } catch (IOException e) {
            e.printStackTrace();
      }
    }
}
