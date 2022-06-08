package com.dq.blog.controller;

import com.dq.blog.service.LoginService;
import com.dq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 退出登录-API
 * @project: blog_parent
 * @ClassName: LoginOutController
 * @author: dq
 * @creat: 2022/5/18 18:06
 */
@RestController
@RequestMapping("logout")
public class LoginOutController {

    @Autowired
    private LoginService loginService;

    /**
     * 退出登录
     * @param token
     * @return
     */
    @GetMapping
    private Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }

}
