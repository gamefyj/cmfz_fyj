package com.baizhi.service;

import com.baizhi.dto.AlbumPageDto;
import com.baizhi.dto.GuruPageDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Guru;

public interface GuruService {
    String insert(Guru guru);
    String update(Guru guru);
    GuruPageDto queryAllByPage(Integer page, Integer rows);
    void modifyProfile(Guru guru);
}
