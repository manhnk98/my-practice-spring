package com.nkm.mypracticespring.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Autowired
    private RestfulRequestFilter restfulRequestFilter;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restfulRequestFilter).addPathPatterns("/v1/api/**").order(2);
        registry.addInterceptor(authInterceptor).addPathPatterns("/v1/api/**").order(1);
    }
}
