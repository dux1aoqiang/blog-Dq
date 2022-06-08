package com.dq.blog.controller;


import com.dq.blog.pojo.SysUser;
import com.dq.blog.util.UserThreadLocal;
import com.dq.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试-API
 * @project: blog_parent
 * @ClassName: TestController
 * @author: dq
 * @creat: 2022/5/19 17:51
 */
@RestController
@RequestMapping("test")
public class TestController {

    /**
     * 测试利用Authorization获取用户注册信息
     * @return
     */
    @GetMapping
    public Result test(){
        //试想一下 如何拿到用户信息
        SysUser sysUser = UserThreadLocal.getLOCAL();
        System.out.println(sysUser);
        return Result.success("成功");
    }
}
