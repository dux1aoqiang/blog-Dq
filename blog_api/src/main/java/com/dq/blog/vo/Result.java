package com.dq.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @project: blog_parent
 * @ClassName: Result
 * @author: dq
 * @creat: 2022/5/16 13:46
 */
@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;

    /**
     * 成功响应
     * @param data
     * @return
     */
    public static Result success(Object data){
        return new Result(true,200,"success", data);
    }

    /**
     * 错误响应
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code,String msg){
        return new Result(true,code,msg,null);
    }

}
