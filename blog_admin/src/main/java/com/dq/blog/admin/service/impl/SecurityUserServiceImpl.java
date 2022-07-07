package com.dq.blog.admin.service.impl;

import com.dq.blog.admin.pojo.Admin;
import com.dq.blog.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @project: blog_parent
 * @ClassName: SecurityUserServiceImpl
 * @author: dq
 * @creat: 2022/7/6 17:25
 */
@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;


    /**
     * 用户的登录认证
     * 查看是否有此人
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录的时候把username 传递到这里
        //通过username查询admin表 如果admin纯在将密码告诉给spring security
        //如果不存在返回null
        Admin admin = adminService.findUserByUsername(username);
        System.out.println("admin"+admin);
        if (admin ==null){//登录失败
            return null;
        }
        UserDetails user = new User(username, admin.getPassword(), new ArrayList<>());
        System.out.println(user);
        return user;
    }
}
