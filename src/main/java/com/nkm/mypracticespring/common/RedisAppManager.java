package com.nkm.mypracticespring.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisAppManager {

    @Autowired
    private RedisTemplate<String, String> sessionManagerRedis;

    public void addSession(String key, String value, Duration expireTime) {
        sessionManagerRedis.opsForValue().set(key, value, expireTime);
    }

    public String getSession(String key) {
        return sessionManagerRedis.opsForValue().get(key);
    }

    public boolean removeSession(String key) {
        return Boolean.TRUE.equals(sessionManagerRedis.delete(key));
    }

}
