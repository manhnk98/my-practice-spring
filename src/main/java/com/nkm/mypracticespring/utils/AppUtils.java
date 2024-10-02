package com.nkm.mypracticespring.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nkm.mypracticespring.common.json_deserializer.SafeBooleanDeserializer;
import com.nkm.mypracticespring.common.json_deserializer.SafeDoubleDeserializer;
import com.nkm.mypracticespring.common.json_deserializer.SafeIntDeserializer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

public class AppUtils {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private AppUtils() {
        this.restTemplate = initRestTemplate();
        this.objectMapper = initObjectMapper();
    }

    private RestTemplate initRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        // không connect được trong N giây => ném lỗi
        requestFactory.setConnectTimeout(Duration.ofSeconds(1));

        // trong N giây không nhận được response => ném lỗi
        requestFactory.setReadTimeout(Duration.ofSeconds(6));
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new NoOpResponseErrorHandler());

        return restTemplate;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // không ném exception khi không tìm thấy field được khai báo trong class
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // set giá trị zero value khi data type của JSON không khớp với data type trong class
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Integer.class, new SafeIntDeserializer());
        module.addDeserializer(int.class, new SafeIntDeserializer());
        module.addDeserializer(Double.class, new SafeDoubleDeserializer());
        module.addDeserializer(double.class, new SafeDoubleDeserializer());
        module.addDeserializer(Boolean.class, new SafeBooleanDeserializer());
        module.addDeserializer(boolean.class, new SafeBooleanDeserializer());

        objectMapper.registerModule(module);

        return objectMapper;
    }

    private static class InstanceHolder {
        private static AppUtils INSTANCE;
    }

    private static AppUtils getAppUtils() {
        if (InstanceHolder.INSTANCE == null) {
            InstanceHolder.INSTANCE = new AppUtils();
        }

        return InstanceHolder.INSTANCE;
    }

    public static RestTemplate restTemplate() {
        AppUtils singleton = getAppUtils();
        return singleton.restTemplate;
    }

    public static ObjectMapper objectMapper() {
        AppUtils singleton = getAppUtils();
        return singleton.objectMapper;
    }

}
