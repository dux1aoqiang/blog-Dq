package com.dq.blog.controller;

import com.dq.blog.common.aop.LogAnnotation;
import com.dq.blog.common.cache.Cache;
import com.dq.blog.service.ArticleService;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.ArticleParam;
import com.dq.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章-API
 *
 * @project: blog_parent
 * @ClassName: 文章-API
 * @author: dq
 * @creat: 2022/5/16 13:42
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 获取首页文章列表
     *
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "listArticle")//自定义缓存
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 获取最热文章
     *
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000, name = "hot-article")//自定义缓存
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 获取最新文章
     *
     * @return
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000, name = "new-article")//自定义缓存
    public Result newsArticle() {
        int limit = 3;
        return articleService.newsArticle(limit);
    }

    /**
     * 获取文章归档数据
     *
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        int limit = 5;
        return articleService.listArchives(limit);
    }


    /**
     * 根据Id 获取文章详情
     *
     * @param id
     * @return
     */
    @PostMapping("view/{id}")
    public Result viewArchives(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    /**
     * 文章发布
     *
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publicArticle(@RequestBody ArticleParam articleParam) {
        return articleService.publishArticle(articleParam);
    }


}
