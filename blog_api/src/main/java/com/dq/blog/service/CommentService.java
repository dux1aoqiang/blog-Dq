package com.dq.blog.service;

import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.CommentParam;

/**
 * @project: blog_parent
 * @ClassName: CommentsService
 * @author: dq
 * @creat: 2022/5/21 16:29
 */
public interface CommentService {
    /**
     * 查询评论列表
     * @param id
     * @return
     */
    Result findCommentsByArticleId(Long id);

    /**
     * 发表评论
     * @param commentParam
     * @return
     */
    Result creatComment(CommentParam commentParam);
}
