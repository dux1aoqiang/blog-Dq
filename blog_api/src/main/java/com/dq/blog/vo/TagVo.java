package com.dq.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: TagVo
 * @author: dq
 * @creat: 2022/5/16 14:02
 */
@Data
public class TagVo {

    private String id;

    private String tagName;

    private String avatar;
}
