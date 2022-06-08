package com.dq.blog.service;

import com.dq.blog.vo.Result;
import com.dq.blog.vo.TagVo;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: TagService
 * @author: dq
 * @creat: 2022/5/16 21:19
 */
public interface TagService {
    /**
     * 根据ArticleId查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    Result hots(int limit);

    /**
     * 查询所有标签
     * @return
     */
    Result findAllTag();

    /**
     * 标签查询 -详细
     * @return
     */
    Result findAllDetail();

    /**
     * 通过Id查询相关标签详情
     * @param id
     * @return
     */
    Result findAllDetailById(Long id);
}
