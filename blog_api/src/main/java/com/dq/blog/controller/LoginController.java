package com.dq.blog.controller;

import com.dq.blog.service.LoginService;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录授权-API
 * @project: blog_parent
 * @ClassName: 登录授权-API
 * @author: dq
 * @creat: 2022/5/17 21:37
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录授权返回Token
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }



}
