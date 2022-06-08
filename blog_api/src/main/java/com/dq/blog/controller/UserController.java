package com.dq.blog.controller;

import com.dq.blog.pojo.SysUser;
import com.dq.blog.service.SysUserService;
import com.dq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息-API
 * @project: blog_parent
 * @ClassName: 用户信息-API
 * @author: dq
 * @creat: 2022/5/18 9:53
 */
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户基本信息 利用Token
     * @param token
     * @return
     */
    @GetMapping("currentUser")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
