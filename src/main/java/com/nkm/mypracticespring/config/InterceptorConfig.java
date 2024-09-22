package com.nkm.mypracticespring.config;

import com.nkm.mypracticespring.security.filter.AuthenticationInterceptor;
import com.nkm.mypracticespring.security.filter.RestfulRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private RestfulRequestInterceptor restfulRequestInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restfulRequestInterceptor).addPathPatterns("/v1/api/**").order(2);
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/v1/api/**").order(1);
    }
}
