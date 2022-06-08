package com.dq.blog.service;

import com.dq.blog.pojo.SysUser;
import com.dq.blog.vo.RegisterVo;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.LoginParams;
import org.springframework.transaction.annotation.Transactional;

/**
 * @project: blog_parent
 * @ClassName: LoginService 事务管理
 * @author: dq
 * @creat: 2022/5/17 21:40
 */
@Transactional
public interface LoginService {

    /**
     * 登录功能授权
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    /**
     * 校验授权的Token
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录 删除token
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param registerVo
     * @return
     */
    Result register(RegisterVo registerVo);
}
