package com.lingb.couponplus.merchant.security;

import com.lingb.couponplus.merchant.constant.Commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author lingb
 * @date 2018.11.15 12:33
 */
@Component
public class AuthCheckInterceptor implements HandlerInterceptor{
    /**
     * 在业务逻辑之前
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = httpServletRequest.getHeader(Commons.TOKEN_STRING);
        if (StringUtils.isEmpty(token)) {
            throw new Exception("Header 缺少" + Commons.TOKEN_STRING);
        }

        if (!token.equals(Commons.TOKEN)) {
            throw new Exception("token 错误！");
        }

        AccessContext.setToken(token);
        return true;
    }

    /**
     * 在业务逻辑之后，但一般不调用！！（执行该方法会因为异常而终止）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在业务逻辑之后，常用！！（即使抛出异常也会执行）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        AccessContext.clearAccessKey();
    }
}
