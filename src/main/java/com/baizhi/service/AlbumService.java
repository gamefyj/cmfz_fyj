package com.baizhi.service;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;

public interface AlbumService {
    String insert(Album album);
    String update(Album album);
    void delete(String id);
    AlbumPageDto queryAllByPage(Integer page, Integer rows);
    void modifyImgPath(Album album);
}
