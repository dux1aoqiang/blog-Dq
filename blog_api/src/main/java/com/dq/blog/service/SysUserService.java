package com.dq.blog.service;

import com.dq.blog.pojo.SysUser;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.UserVo;

/**
 * @project: blog_parent
 * @ClassName: SysUserService
 * @author: dq
 * @creat: 2022/5/17 10:35
 */
public interface SysUserService {
    /**
     * 根据userId 查询用户
     * @param userId
     * @return
     */
    SysUser findUserById(Long userId);

    /**
     * 登录 查询用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token 获取UserInfo
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser finUserByAccount(String account);

    /**
     * 保存注册用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据Comment中articleId查询相应的作者信息
     * @param articleId
     * @return
     */
    UserVo findUserByCommentArticleId(Long articleId);

    /**
     * 根据Comment中toUserId查询相应的作者信息
     * @param toUserId
     * @return
     */
    UserVo findUserByCommentToUserId(Long toUserId);
}
