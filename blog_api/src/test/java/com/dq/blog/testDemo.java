package com.dq.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @project: blog_parent
 * @ClassName: testDemo
 * @author: dq
 * @creat: 2022/5/27 17:14
 */
@SpringBootTest
public class testDemo {

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Test
    void test(){
        System.out.println(accessKey);
    }
}
