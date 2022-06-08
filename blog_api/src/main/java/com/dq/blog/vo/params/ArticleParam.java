package com.dq.blog.vo.params;

import com.dq.blog.vo.CategoryVo;
import com.dq.blog.vo.TagVo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * ArticleParam
 */
@Data
public class ArticleParam {


    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}