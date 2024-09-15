package com.nkm.mypracticespring.controller.test;

import com.nkm.mypracticespring.dto.ResponseDto;
import com.nkm.mypracticespring.dto.test.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/redis")
    public ResponseDto<Long> publishMessage(@RequestBody MessageDto messageDto) {
        return new ResponseDto<>(redisTemplate.convertAndSend(messageDto.getChannel(), messageDto.getMessage()));
    }

}
