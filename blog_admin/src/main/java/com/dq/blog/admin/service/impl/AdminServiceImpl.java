package com.dq.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dq.blog.admin.mapper.AdminMapper;
import com.dq.blog.admin.pojo.Admin;
import com.dq.blog.admin.pojo.Permission;
import com.dq.blog.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: AdminServiceImpl
 * @author: dq
 * @creat: 2022/7/6 17:28
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findUserByUsername(String username) {
        /**
         * 查询username
         */
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        //保证查出一条数据
        queryWrapper.eq(Admin::getUsername,username).last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByAdminId(Long adminId) {
        //sql 语句SELECT * FROM `ms_permission` WHERE id in (SELECT ms_admin_permission.permission_id FROM ms_admin_permission WHERE ms_admin_permission.admin_id = 1)
        return adminMapper.findPermissionByAdminId(adminId);
    }
}
