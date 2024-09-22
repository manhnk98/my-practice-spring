package com.nkm.mypracticespring.utils;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Log4j2
public class RestfulUtils {

    public static <T> ResponseEntity<T> postRequest(String url, Map<String, String> mapHeaders, JSONObject bodyRequest, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> entry : mapHeaders.entrySet()) {
            httpHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<?> httpEntity = new HttpEntity<>(bodyRequest.toString(), httpHeaders);

        log.info("POST =====> REQUEST[{}] => data[{}]", url, httpEntity);
        ResponseEntity<T> response = RestTemplateSingleton.get().postForEntity(url, httpEntity, responseType);
        log.info("POST =====> RESPONSE[{}] => data[{}]", url, response);

        return response;
    }

}
