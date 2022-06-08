package com.dq.blog.controller;

import com.dq.blog.service.CategoryService;
import com.dq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * 发布文章-API
 * @project: blog_parent
 * @ClassName: CategoryController
 * @author: dq
 * @creat: 2022/5/24 21:22
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 文章分类
     */
    @GetMapping
    public Result categorys() {
        return categoryService.findAllCategory();
    }

    /**
     * 文章标签分类-详细
     * @return
     */
    @GetMapping("detail")
    public Result findDetail(){
        return categoryService.findDetail();
    }

    /**
     * 根据分类Id获取文章
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result findCategoryDetailById(@PathVariable("id")  Long id){
        return categoryService.findCategoryDetailById(id);
    }
}
