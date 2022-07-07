package com.dq.blog.admin.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: AuthService
 * @author: dq
 * @creat: 2022/7/6 18:08
 */
public interface AuthService {
    /**
     * 权限认证  url的比对
     * @param httpServletRequest
     * @param authentication
     * @return
     */
    public boolean auth(HttpServletRequest httpServletRequest, Authentication authentication);
}
