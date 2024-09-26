package com.nkm.mypracticespring.config;

import com.nkm.mypracticespring.security.interceptor.RestfulRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorRegistry implements WebMvcConfigurer {

    @Autowired
    private RestfulRequestInterceptor restfulRequestInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(restfulRequestInterceptor)
                .addPathPatterns("/v1/api/**")
                .excludePathPatterns(SecurityConfig.WHITE_LIST_API)
                .order(1);
    }
}
