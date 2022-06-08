package com.dq.blog.admin.model.params;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: PageParam
 * @author: dq
 * @creat: 2022/6/4 12:51
 */
@Data
public class PageParam {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
