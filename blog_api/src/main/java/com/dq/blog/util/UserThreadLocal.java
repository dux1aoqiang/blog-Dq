package com.dq.blog.util;

import com.dq.blog.pojo.SysUser;

/**
 * @project: blog_parent
 * @ClassName: UserThreadLocal  保证线程安全问题
 * @author: dq
 * @creat: 2022/5/19 20:14
 */
public class UserThreadLocal {
    private UserThreadLocal(){};

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void setLocal(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser getLOCAL() {
        return LOCAL.get();
    }

    public static void deleteLocal(){
        LOCAL.remove();
    }
}
