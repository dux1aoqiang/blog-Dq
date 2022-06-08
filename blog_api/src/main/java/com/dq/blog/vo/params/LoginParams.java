package com.dq.blog.vo.params;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: LoginParams
 * @author: dq
 * @creat: 2022/5/17 21:40
 */
@Data
public class LoginParams {
    //用户名
    private String account;

    //密码
    private String password;
}
