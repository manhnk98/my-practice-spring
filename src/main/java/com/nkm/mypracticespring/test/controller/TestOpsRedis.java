package com.nkm.mypracticespring.test.controller;

import com.nkm.mypracticespring.dto.common.ResponseDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test/redis")
public class TestOpsRedis {

    @Autowired
    private RedisTemplate<String, String> sessionManagerRedis;

    @SneakyThrows
    @GetMapping
    public ResponseDto<?> getOps() {
        String key = "session_66f79cc15297a846043c853a_66f838898519f943de13b734manhnk";
        String value = "ManhNK_Test_value";
        sessionManagerRedis.opsForValue().set(key, value, Duration.ofMinutes(2));
        System.out.println("value before : " + sessionManagerRedis.opsForValue().get(key));
        TimeUnit.SECONDS.sleep(15);
        System.out.println("null value => : " + sessionManagerRedis.opsForValue().get(key));
        sessionManagerRedis.opsForValue().set(key, value, Duration.ofMinutes(2));
        System.out.println("value after : " + sessionManagerRedis.opsForValue().get(key));
        return new ResponseDto<>(sessionManagerRedis.opsForValue().get(key));
    }

}
