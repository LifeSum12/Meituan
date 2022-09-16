package com.leechr.meituan.filter;

import com.alibaba.fastjson.JSON;
import com.leechr.meituan.common.BaseContext;
import com.leechr.meituan.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.PathMatcher;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        String[] urls = new String[]{
//                "/employee/page",
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login"
        };
        //如果符合uri，放行
        if(check(urls,requestURI)){
            filterChain.doFilter(request,response);
            return;
        }
        //employee: 如果已经登录，放行
        if(request.getSession().getAttribute("employee") != null){
            //为线程set一个ID，解决公共字段自动填充问题
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        //user: 如果已经登录，放行
        if(request.getSession().getAttribute("user") != null){
            //为线程set一个ID，解决公共字段自动填充问题
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //如果未登录，通过输出流方式返回 R对象给客户端（因为不是在controller，不可以直接返回）
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN"))); //先转为JSON再返回
        return;
    }

    public boolean check(String[] urls, String requestURI){
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
