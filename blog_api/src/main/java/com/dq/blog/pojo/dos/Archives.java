package com.dq.blog.pojo.dos;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: Archives  用于归档文件
 * @author: dq
 * @creat: 2022/5/17 14:49
 */
@Data
public class Archives {
//年
private Integer year;
//月
private Integer month;
//日
private Long count;

}
