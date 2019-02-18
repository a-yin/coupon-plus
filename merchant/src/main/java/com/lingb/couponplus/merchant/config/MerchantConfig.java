package com.lingb.couponplus.merchant.config;

import com.lingb.couponplus.merchant.security.AuthCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置类
 *
 * @author lingb
 * @date 2018.11.17 16:54
 */
@Component
public class MerchantConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
                .addPathPatterns("/merchant/**");
        super.addInterceptors(registry);
    }

}
