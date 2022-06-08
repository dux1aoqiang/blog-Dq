package com.dq.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: PageResult
 * @author: dq
 * @creat: 2022/6/4 20:23
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}
