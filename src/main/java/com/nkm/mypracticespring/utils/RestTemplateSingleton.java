package com.nkm.mypracticespring.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class RestTemplateSingleton {

    private final RestTemplate restTemplate;

    private RestTemplateSingleton() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
        requestFactory.setReadTimeout(Duration.ofSeconds(6));

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new NoOpResponseErrorHandler());

        this.restTemplate = restTemplate;
    }

    private static class InstanceHolder {
        private static RestTemplateSingleton INSTANCE;
    }

    private static RestTemplateSingleton getRestTemplateInstance() {
        if (InstanceHolder.INSTANCE == null) {
            InstanceHolder.INSTANCE = new RestTemplateSingleton();
        }

        return InstanceHolder.INSTANCE;
    }

    public static RestTemplate get() {
        RestTemplateSingleton singleton = getRestTemplateInstance();
        return singleton.restTemplate;
    }

}
