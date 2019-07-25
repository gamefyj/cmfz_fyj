package com.baizhi.service;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.ChapterPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;

public interface ChapterService {
    String insert(Chapter chapter);
    String update(Chapter chapter);
    void delete(String id);
    ChapterPageDto queryAllByPage(Integer page, Integer rows,String id);
    void modifyImgPath(Chapter chapter);
}
