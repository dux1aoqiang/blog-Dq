package com.dq.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: UserVo
 * @author: dq
 * @creat: 2022/5/21 16:07
 */
@Data
public class UserVo {

    private String nickname;

    private String avatar;
    // @JsonSerialize(using = ToStringSerializer.class)
    private String id;
}
