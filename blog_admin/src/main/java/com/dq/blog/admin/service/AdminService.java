package com.dq.blog.admin.service;

import com.dq.blog.admin.pojo.Admin;
import com.dq.blog.admin.pojo.Permission;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: AdminService
 * @author: dq
 * @creat: 2022/7/6 17:28
 */
public interface AdminService {

    /**
     * 根据用户名查询是否有此人并且将password给UserDetails
     * @param username
     * @return
     */
    public Admin findUserByUsername(String username);

    public List<Permission> findPermissionByAdminId(Long adminId);

}
