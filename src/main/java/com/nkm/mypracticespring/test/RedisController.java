package com.nkm.mypracticespring.test;

import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.dto.test.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_KEY = "refresh_token_lock";

    private static final long LOCK_EXPIRE_TIME = 60000L; // 60 second

    @PostMapping("/redis")
    public ResponseDto<Long> publishMessage(@RequestBody MessageDto messageDto) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(LOCK_KEY, "locked", LOCK_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return new ResponseDto<>(redisTemplate.convertAndSend(messageDto.getChannel(), messageDto.getMessage()));
    }

}
