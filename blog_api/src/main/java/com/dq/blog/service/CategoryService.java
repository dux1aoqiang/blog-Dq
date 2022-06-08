package com.dq.blog.service;

import com.dq.blog.vo.CategoryVo;
import com.dq.blog.vo.Result;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: CategoryService
 * @author: dq
 * @creat: 2022/5/20 11:45
 */
public interface CategoryService {
    /**
     * 查询类别
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);

    /**
     * 文章类别查询
     */
    Result findAllCategory();

    /**
     * 详细获取文章
     * @return
     */
    Result findDetail();

    /**
     * 根据文章分类id 获取
     * @param id
     * @return
     */
    Result findCategoryDetailById(Long id);
}
