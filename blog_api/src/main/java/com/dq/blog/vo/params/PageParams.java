package com.dq.blog.vo.params;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: PageParams
 * @author: dq
 * @creat: 2022/5/16 13:44
 */
@Data
public class PageParams {
    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    /**
     * 处理
     * 如1 ->01
     * @return
     */
    public String getMonth() {
        if (this.month!=null &&this.month.length()==1){
            return "0"+this.month;
        }
        return this.month;
    }
}
