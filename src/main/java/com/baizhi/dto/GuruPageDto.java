package com.baizhi.dto;


import com.baizhi.entity.Guru;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuruPageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Guru> rows;
}
