package com.dq.blog.vo.params;

import com.dq.blog.pojo.Article;
import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: CommentParam
 * @author: dq
 * @creat: 2022/5/23 13:21
 */
@Data
public class CommentParam {

    private Article article;

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
