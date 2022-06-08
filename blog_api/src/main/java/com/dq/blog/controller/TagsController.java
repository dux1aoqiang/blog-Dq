package com.dq.blog.controller;

import com.dq.blog.service.TagService;
import com.dq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签-API
 * @project: blog_parent
 * @ClassName: 标签-API
 * @author: dq
 * @creat: 2022/5/17 12:55
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    /**
     * 查询最热标签
     * @return
     */
    @GetMapping("hot")
    public Result hot(){
        int limit = 6;
        return tagService.hots(limit);
    }

    /**
     * 获取所有的标签
     * @return
     */
    @GetMapping
    public Result findAllTag(){
        return tagService.findAllTag();
    }

    /**
     * 获取标签的详细
     * @return
     */
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    /**
     * 通过标签的Id获取所有相关标签
     * @return
     */
    @GetMapping("detail/{id}")
    public Result findAllDetailById(@PathVariable("id") Long id){
        return tagService.findAllDetailById(id);
    }
}
