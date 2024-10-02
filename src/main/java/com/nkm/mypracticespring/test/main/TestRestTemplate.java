package com.nkm.mypracticespring.test.main;

import com.nkm.mypracticespring.utils.RestfulUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.*;

import java.util.HashMap;

public class TestRestTemplate {

    public static void main(String[] args) {
        ResponseEntity<String> res = RestfulUtils.postRequest("http://localhost:8081/test/post-timeout", new HashMap<>(), new JSONObject(), String.class);
        System.out.println(res.getBody());

//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
////        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
////        requestFactory.setReadTimeout(Duration.ofSeconds(3));
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setErrorHandler(new NoOpResponseErrorHandler());
//        ResponseEntity<?> responseErr = null;
//        try {
//            ResponseEntity<?> responseOK = restTemplate.getForEntity("http://localhost1:8088/test/get-timeout", Object.class);
//            System.out.println(responseOK.getBody());
////            ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:8088/test/post-timeout", "", Object.class);
////            System.out.println(response.getBody());
//        } catch (Exception e) {
//            if (e instanceof HttpStatusCodeException ex1) {
//                responseErr = new ResponseEntity<>(ex1.getResponseBodyAsString(), ex1.getResponseHeaders(), ex1.getStatusCode());
//            } else if (e instanceof ResourceAccessException exResource) {
//                System.out.println(exResource);
//            } else if (e instanceof IllegalArgumentException exArgument) {
//                System.out.println(exArgument.getMessage());
//            } else {
//                System.out.println(e.getMessage());
//            }
//        }
//        System.out.println(responseErr);
    }

}
