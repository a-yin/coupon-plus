package com.lingb.couponplus.merchant.security;

/**
 * 用 ThreadLocal 单独存储每个线程携带的token 信息
 *
 * @author lingb
 * @date 2018.11.15 11:41
 */
public class AccessContext {

    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String getToken() {
        return token.get();
    }

    public static void setToken(String tokenStr) {
        token.set(tokenStr);
    }

    public static void clearAccessKey() {
        token.remove();
    }
}
