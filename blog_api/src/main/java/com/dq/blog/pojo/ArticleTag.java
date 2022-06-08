package com.dq.blog.pojo;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: ArticleTag
 * @author: dq
 * @creat: 2022/5/25 13:30
 */
@Data
public class ArticleTag {
    private Long id;

    private Long articleId;

    private Long tagId;
}
