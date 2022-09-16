package com.leechr.meituan.common;

/**
 * 当前页面需要获取此时登录的user或employee ID时。
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
