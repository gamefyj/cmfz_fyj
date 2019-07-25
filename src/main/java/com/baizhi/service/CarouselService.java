package com.baizhi.service;

import com.baizhi.dto.CarouselPageDto;
import com.baizhi.entity.Carousel;

public interface CarouselService {
    //int queryCount();
    String insert(Carousel e);
    String update(Carousel e);
    void delete(String id);
    Carousel queryOne(String id);

    CarouselPageDto queryAllByPage(Integer page, Integer rows);
    void modifyImgPath(Carousel carousel);
}
