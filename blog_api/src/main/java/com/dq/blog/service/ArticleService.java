package com.dq.blog.service;

import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.ArticleParam;
import com.dq.blog.vo.params.PageParams;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @project: blog_parent
 * @ClassName: ArticleService
 * @author: dq
 * @creat: 2022/5/16 13:52
 */

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 查询最热文章 5条
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 查询最热文章 3条
     * @param limit
     * @return
     */
    Result newsArticle(int limit);

    /**
     * 文章归档数据 5条
     * @param limit
     * @return
     */
    Result listArchives(int limit);

    /**
     * 根据文章id 获取具体markdown 文本
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    /**
     * 发布文章返回ID 进行跳转
     * @param articleParam
     * @return
     */
    Result publishArticle(ArticleParam articleParam);
}
