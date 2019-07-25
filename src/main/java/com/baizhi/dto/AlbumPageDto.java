package com.baizhi.dto;

import com.baizhi.entity.Album;
import com.baizhi.entity.Carousel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Album> rows;
}
