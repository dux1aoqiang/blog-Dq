package com.dq.blog.vo;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: RegisterVo
 * @author: dq
 * @creat: 2022/5/19 12:11
 */
@Data
public class RegisterVo {

    private String account;

    private String password;

    private String nickname;
}
