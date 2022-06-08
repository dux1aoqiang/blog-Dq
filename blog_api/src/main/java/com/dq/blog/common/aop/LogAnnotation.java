package com.dq.blog.common.aop;

import java.lang.annotation.*;

/**
 * 切面通知 监听接口数据
 * @project: blog_parent
 * @ClassName: LogAnnotation
 * @author: dq
 * @creat: 2022/5/27 13:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    //模块名称
    String module() default "";

    //操作名称
    String operator() default "";
}
