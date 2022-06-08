package com.dq.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dq.blog.pojo.SysUser;
import com.dq.blog.service.LoginService;
import com.dq.blog.service.SysUserService;
import com.dq.blog.util.JWTUtils;
import com.dq.blog.vo.ErrorCode;
import com.dq.blog.vo.RegisterVo;
import com.dq.blog.vo.Result;
import com.dq.blog.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @project: blog_parent
 * @ClassName: LoginServiceImpl
 * @author: dq
 * @creat: 2022/5/18 8:49
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    //加密盐
    private static final String salt = "Dq333";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1 检查参数是否合法
         * 2 根据用户名和密码是否存在
         * 3 存在 成功（使用JWT生成token）  不存在 失败
         * 4 存入redis token：user 信息 设置过期时间（登录认证时 先认证token是否纯在  再去redis校验）
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //加密 +盐
        password = DigestUtils.md5Hex(password + salt);
        // log.info("password ===>{}",password);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.error(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        //根据唯一Id 生成Token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        //校验token是否纯在
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //校验token是否合法
        Map<String, Object> objectMap = JWTUtils.checkToken(token);
        if (objectMap == null) {
            return null;
        }
        //校验redis中是否有此token的User信息
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        //检查是否过期
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        //解析user数据
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(RegisterVo registerVo) {
        /**
         * 1    判断参数是否合法
         * 2    判断账户是否纯在  、 如果存在 账户已经被注册
         * 3    如果账户不存在 、 注册用户
         * 4    生成token
         * 5    存入redis 并返回
         * 6    注意 加上事务  如果出现错误 则回滚
         */

        String account = registerVo.getAccount();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.finUserByAccount(account);
        if (sysUser != null) {
            return Result.error(ErrorCode.ACCOUNT_EXIST.getCode(), "账户已被注册");
        }
        sysUser = new SysUser();
        //注册用户
        sysUser.setNickname(registerVo.getNickname());
        sysUser.setAccount(registerVo.getAccount());
        //密码加密 加入盐
        sysUser.setPassword(DigestUtils.md5Hex(registerVo.getPassword() + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("https://tse3-mm.cn.bing.net/th/id/OIP-C.ZaMvIRseijoVQNlpU2GABQAAAA?pid=ImgDet&rs=1");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        //生成Token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }
}
