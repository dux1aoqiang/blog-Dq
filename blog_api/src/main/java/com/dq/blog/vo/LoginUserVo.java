package com.dq.blog.vo;

import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: LoginUserVo
 * @author: dq
 * @creat: 2022/5/18 10:43
 */
@Data
public class LoginUserVo {
    //id号
    private String id;

    //用户名
    private String account;

    //别名
    private String nickname;

    //图片头像
    private String avatar;

}
