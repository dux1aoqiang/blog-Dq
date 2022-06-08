package com.dq.blog.common.cache;

import java.lang.annotation.*;

/**
 * 缓存优化
 * @project: blog_parent
 * @ClassName: Cache
 * @author: dq
 * @creat: 2022/5/30 19:25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    //过期时间
    long expire() default 1*60*1000;

    String name() default "";
}
