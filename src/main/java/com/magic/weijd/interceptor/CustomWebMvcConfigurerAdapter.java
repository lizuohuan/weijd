package com.magic.weijd.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 启用拦截器 装配
 * @author lzh
 * @create 2017/9/12 20:07
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/wechat/**");  //对来自/user/** 这个链接来的请求进行拦截
        registry.addInterceptor(new AdminLoginInterceptor()).addPathPatterns("/admin/**");  //对来自/user/** 这个链接来的请求进行拦截

    }
}
