package com.dq.blog.admin.service.impl;


import com.dq.blog.admin.pojo.Admin;
import com.dq.blog.admin.pojo.Permission;
import com.dq.blog.admin.service.AdminService;
import com.dq.blog.admin.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: AuthServiceImpl
 * @author: dq
 * @creat: 2022/7/6 17:51
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AdminService adminService;
    /**
     * 写权限认证
     * @param request
     * @param authentication
     * @return
     */
    public boolean auth(HttpServletRequest request, Authentication authentication) {
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        //如果登录认证拿到的是空或者匿名用户
        if (principal ==null || "anonymousUser".equals(principal)){
            return false; //未登录
        }
        //如果查有此人 获取他的权限表
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findUserByUsername(username);
        if (admin==null){
            return false;
        }
        Long adminId = admin.getId();
        //超级管理员
        if (adminId ==1){
            return true;
        }

        //根据id查询用户权限
        List<Permission> adminPermissionList = adminService.findPermissionByAdminId(adminId);
        //要保证url只有请求 不带参数
        requestURI=StringUtils.split(requestURI,"?")[0];
        for (Permission permission : adminPermissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}
