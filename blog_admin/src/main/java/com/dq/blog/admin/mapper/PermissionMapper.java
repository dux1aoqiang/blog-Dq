package com.dq.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dq.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @project: blog_parent
 * @ClassName: PermissionMapper
 * @author: dq
 * @creat: 2022/6/4 20:21
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
