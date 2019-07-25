package com.baizhi.dto;

import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<Article> rows;
}
