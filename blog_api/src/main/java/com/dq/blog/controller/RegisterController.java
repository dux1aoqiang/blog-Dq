package com.dq.blog.controller;

import com.dq.blog.service.LoginService;
import com.dq.blog.vo.RegisterVo;
import com.dq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册-API
 * @project: blog_parent
 * @ClassName: 祖册-API
 * @author: dq
 * @creat: 2022/5/19 12:10
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    /**
     * 注册用户
     * @param registerVo
     * @return
     */
    @PostMapping
    public Result registerUser(@RequestBody RegisterVo registerVo){
        //sso 单点登录  后期可以把service 单独提供接口服务
        return loginService.register(registerVo);
    }

}
