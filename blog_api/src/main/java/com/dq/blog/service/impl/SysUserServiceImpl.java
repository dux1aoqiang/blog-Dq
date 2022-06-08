package com.dq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dq.blog.mapper.SysUserMapper;
import com.dq.blog.pojo.SysUser;
import com.dq.blog.service.LoginService;
import com.dq.blog.service.SysUserService;
import com.dq.blog.vo.ErrorCode;
import com.dq.blog.vo.LoginUserVo;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @project: blog_parent
 * @ClassName: SysUserServiceImpl
 * @author: dq
 * @creat: 2022/5/17 10:36
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("Dq333");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUser::getAccount, account)
                .eq(SysUser::getPassword, password)
                .select(SysUser::getId,SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname)
                .last("limit " + 1);

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1    首先token合法性校验
         *      是否为空，解析是否成功，redis是否纯在
         * 2    如果校验失败 返回错误信息
         * 3    如果成果  返回对应结果  LoginUserVo
         */
        //利用Login中的校验方法  校验上述步骤  成果返回SysUser
        SysUser sysUser = loginService.checkToken(token);
        //如果null 说明校验失败
        if (sysUser == null) {
            return Result.error(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return Result.success(loginUserVo);
    }

    @Override
    public SysUser finUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SysUser::getAccount, account)
                .last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //保存用户 id自动生成
        //mybatis-plus框架 默认id雪花算法
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserByCommentArticleId(Long articleId) {
        /**
         * 1. 根据articleid查询昵称 图片 id
         */
        SysUser sysUser = sysUserMapper.selectById(articleId);

        //如果没有参数 防止报错给入默认值
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("https://tse3-mm.cn.bing.net/th/id/OIP-C.ZaMvIRseijoVQNlpU2GABQAAAA?pid=ImgDet&rs=1");
            sysUser.setNickname("Dq333");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public UserVo findUserByCommentToUserId(Long toUserId) {
        /**
         * 1. 根据articleid查询昵称 图片 id
         */
        SysUser sysUser = sysUserMapper.selectById(toUserId);

        //如果没有参数 防止报错给入默认值
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("https://tse3-mm.cn.bing.net/th/id/OIP-C.ZaMvIRseijoVQNlpU2GABQAAAA?pid=ImgDet&rs=1");
            sysUser.setNickname("Dq333");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }


}
