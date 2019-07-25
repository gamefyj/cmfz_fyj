package com.baizhi.controller;

import com.baizhi.dto.ArticlePageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ArticleService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService service1;
    @RequestMapping("queryByPage")
    @ResponseBody
    public ArticlePageDto queryByPage(Integer page, Integer rows, String id){
        return service1.queryAllByPage(page,rows,id);
    }
    @ResponseBody
    @RequestMapping("queryOne")
    public Article addArticle(String id){
        Article article = service1.queryOne(id);
        return  article;
    }

    @ResponseBody
    @RequestMapping("add")
    public String addArticle(Article article){
        String s = service1.insert(article);
        return  s;
    }


    @RequestMapping("edit")
    @ResponseBody
    public String edit(Article article, String oper,String[] id) throws IOException {
         if("edit".equals(oper)) {
            String s = service1.update(article);
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
    public Map<String , Object> upload(MultipartFile file, HttpServletRequest request){
        String originalFilename = file.getOriginalFilename();
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file1 = new File(articlePic);
        if (!file1.exists()){
            file1.mkdir();
        }
        Map<String , Object> map = new HashMap<>();

        try {
            file.transferTo(new File(articlePic,originalFilename));
            map.put("error",0);
            map.put("url","http://localhost:8888/cmfz/articlePic/"+originalFilename);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","http://localhost:8888/cmfz/articlePic/"+originalFilename);
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("showAll")
    public Map<String , Object> showAll(HttpServletRequest request){
        String articlePic = request.getSession().getServletContext().getRealPath("articlePic");
        File file = new File(articlePic);
        String[] list = file.list();
        Map<String , Object> map = new HashMap<>();
            map.put("current_url","http://localhost:8888/cmfz/articlePic/");
        map.put("total_count",list.length);
        List<Object> lists = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String , Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            File file1 = new File(articlePic,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            lists.add(map1);
        }
        map.put("file_list",lists);
        return map;
    }
}
