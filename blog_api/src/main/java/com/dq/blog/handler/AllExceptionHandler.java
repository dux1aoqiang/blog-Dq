package com.dq.blog.handler;

import com.dq.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @project: blog_parent
 * @ClassName: AllExceptionHandler
 * @author: dq
 * @creat: 2022/5/17 13:56
 */
@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e){
        e.printStackTrace();
        return Result.error(-999,"系统异常");
    }
}
