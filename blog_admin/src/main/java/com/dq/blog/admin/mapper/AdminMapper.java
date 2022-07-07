package com.dq.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dq.blog.admin.pojo.Admin;
import com.dq.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @project: blog_parent
 * @ClassName: AdminMapper
 * @author: dq
 * @creat: 2022/7/6 17:29
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 更具一个用户的id 查询用户的所有权限
     * @param adminId
     * @return
     */
    List<Permission> findPermissionByAdminId(Long adminId);
}
