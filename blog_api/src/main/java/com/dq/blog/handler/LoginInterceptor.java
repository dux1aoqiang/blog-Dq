package com.dq.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dq.blog.pojo.SysUser;
import com.dq.blog.service.LoginService;
import com.dq.blog.util.UserThreadLocal;
import com.dq.blog.vo.ErrorCode;
import com.dq.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project: blog_parent
 * @ClassName: LoginInterceptor
 * @author: dq
 * @creat: 2022/5/19 16:00
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行方法之前执行
        /**
         * 1. 需要判断 请求是否为controller
         * 2. 判断token是否为空 如果为空 未登录
         * 3. 如果token不为空  登录验证 loginservice  checkToken 认证
         * 认证成果 放行
         */
        if (!(handler instanceof HandlerMethod)) {
            //handler可能是资源
            return true;
        }

        String authorization = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", authorization);
        log.info("=================request end===========================");
        //判断authorization_Token是否为空
        if (StringUtils.isBlank(authorization)){
            Result result = Result.error(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //把result转化为json 设置返回json数据
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //判断authorization_Token是否有效
        SysUser sysUser = loginService.checkToken(authorization);
        if (sysUser ==null){
            Result result = Result.error(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //把result转化为json 设置返回json数据
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.setLocal(sysUser);
        return true;
    }

    /**
     * 多线程  每次结束防止内存泄漏
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.deleteLocal();
    }
}
