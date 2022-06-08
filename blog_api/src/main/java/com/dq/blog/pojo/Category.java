package com.dq.blog.pojo;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: Category
 * @author: dq
 * @creat: 2022/5/19 21:41
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
