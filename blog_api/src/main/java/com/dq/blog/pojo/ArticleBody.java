package com.dq.blog.pojo;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: ArticleBody
 * @author: dq
 * @creat: 2022/5/19 21:39
 */
@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}
