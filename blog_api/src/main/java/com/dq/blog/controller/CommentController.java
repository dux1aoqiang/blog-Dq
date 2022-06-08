package com.dq.blog.controller;

import com.dq.blog.pojo.Article;
import com.dq.blog.service.CommentService;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论-API
 * @project: blog_parent
 * @ClassName: CommentController
 * @author: dq
 * @creat: 2022/5/21 16:25
 */
@RestController
@RequestMapping("comments")
public class CommentController {
@Autowired
private CommentService commentsService;

    /**
     * 通过id 获取评论列表
     * @param id 1
     * @return
     */
    @GetMapping("/article/{id}")
    public Result findComments(@PathVariable("id") Long id){

        return  commentsService.findCommentsByArticleId(id);
    }

    /**
     * 发布文章评论
     * @param commentParam
     * @return
     */
    @PostMapping("/create/change")
    public Result findComments(@RequestBody CommentParam commentParam){
        return  commentsService.creatComment(commentParam);
    }
}
