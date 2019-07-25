package com.baizhi.controller;

import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Chapter;
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

@Controller
@RequestMapping("p3")
public class ChapterController {
    @Autowired
    private ChapterService service1;
    @RequestMapping("queryByPage")
    @ResponseBody
    public ChapterPageDto queryByPage(Integer page, Integer rows, String id){

        return service1.queryAllByPage(page,rows,id);
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(Chapter chapter, String oper,String[] id) throws IOException {
        if("add".equals(oper)) {
            String s = service1.insert(chapter);
            return s;
        }else if("edit".equals(oper)) {
            String s = service1.update(chapter);
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
    public void fileiupload(MultipartFile downPath, String id, HttpServletRequest request , HttpServletResponse response){

    String filename = downPath.getOriginalFilename();
    String path = request.getSession().getServletContext().getRealPath("upload");
    File file =new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        try {
            downPath.transferTo(new File(path,filename));
            //修改数据库路径
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setDownPath(filename);
            service1.modifyImgPath(chapter);
        } catch (IOException e) {
            e.printStackTrace();
      }
    }
    @RequestMapping("down")
    public void down(String fname, HttpSession session, HttpServletResponse response) throws IOException {
        //获取load真实路径
        String path = session.getServletContext().getRealPath("/upload");
        //获取源文件的字节数组
        byte[] bytes = FileUtils.readFileToByteArray(new File(path + "/" + fname));
        //获取输出流
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fname,"utf-8"));
        ServletOutputStream out = response.getOutputStream();

        out.write(bytes);
    }
}
