package com.dq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dq.blog.mapper.CategoryMapper;
import com.dq.blog.pojo.Category;
import com.dq.blog.service.CategoryService;
import com.dq.blog.vo.CategoryVo;
import com.dq.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: CategoryServiceImpl
 * @author: dq
 * @creat: 2022/5/20 11:45
 */
@Service
// @EnableTransactionManagement
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAllCategory() {
        /**
         * 1. 文章分类查询
         *     只获取文章的标题和id  用于上传时的展示
         */
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return Result.success(copyList(categories));
    }

    @Override
    public Result findDetail() {
        /**
         * 详细获取文章的所有类容
         */
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);
        return Result.success(copyList(categoryList));
    }

    @Override
    public Result findCategoryDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        return Result.success(copy(category));
    }

    /**
     * 映射CategoryVo中
     * @param categories
     * @return
     */
    private List<CategoryVo> copyList(List<Category> categories) {
        List<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }

    /**
     * 单体映射
     * @param category
     * @return
     */
    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setId(String.valueOf(category.getId()));
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }


}
