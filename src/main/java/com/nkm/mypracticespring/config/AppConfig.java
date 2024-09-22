//package com.nkm.mypracticespring.config;
//
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.DefaultResponseErrorHandler;
//import org.springframework.web.client.NoOpResponseErrorHandler;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.Duration;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
//        requestFactory.setReadTimeout(Duration.ofSeconds(6));
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setErrorHandler(new NoOpResponseErrorHandler());
//
//        return restTemplate;
//    }
//
//}
