package com.dq.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: Admin
 * @author: dq
 * @creat: 2022/7/6 17:29
 */
@Data
public class Admin {
    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //姓名
    private String username;
    //密码
    private String password;
}
