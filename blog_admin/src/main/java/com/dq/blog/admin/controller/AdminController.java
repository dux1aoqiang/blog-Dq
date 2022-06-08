package com.dq.blog.admin.controller;

import com.dq.blog.admin.model.params.PageParam;
import com.dq.blog.admin.pojo.Permission;
import com.dq.blog.admin.service.PermissionService;
import com.dq.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理-API
 * @project: blog_parent
 * @ClassName: AdminController
 * @author: dq
 * @creat: 2022/6/4 12:47
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 分页查询
     * @return
     */
    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam) {
        return permissionService.listPermission(pageParam);
    }

    /**
     * 添加用户
     * @param permission
     * @return
     */
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    /**
     * 根据id跟新操作
     * @param permission
     * @return
     */
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
