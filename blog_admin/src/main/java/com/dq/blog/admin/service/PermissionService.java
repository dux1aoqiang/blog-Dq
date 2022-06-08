package com.dq.blog.admin.service;

import com.dq.blog.admin.model.params.PageParam;
import com.dq.blog.admin.pojo.Permission;
import com.dq.blog.admin.vo.Result;

/**
 * @project: blog_parent
 * @ClassName: PermissionService
 * @author: dq
 * @creat: 2022/6/4 12:53
 */
public interface PermissionService {
    /**
     * 查询分页类容
     * @param pageParam
     * @return
     */
    Result listPermission(PageParam pageParam);

    /**
     * 添加用户
     * @param permission
     * @return
     */
    Result add(Permission permission);

    /**
     * 修改参数
     * @param permission
     * @return
     */
    Result update(Permission permission);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Result delete(Long id);
}
