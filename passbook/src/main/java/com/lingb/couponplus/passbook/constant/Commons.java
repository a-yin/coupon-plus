package com.lingb.couponplus.passbook.constant;

/**
 * 通用常量
 *
 * @author lingb
 * @date 2018.11.13 15:38
 */
public class Commons {

    /**
     * 商户优惠券 KAfka Topic
     */
    public static final String TEMPLATE_TOPIC = "merchant-template";

    /**
     * token 文件存储目录
     */
    public static final String TOKEN_DIR = "/tmp/token/";

    /**
     * 已使用的 token 文件后缀名
     */
    public static final String USED_TOKEN_SUFFIX = "_";

    /**
     * 用户数的 redis key
     */
    public static final String USE_COUNT_REDIS_KEY = "user-count";

}
