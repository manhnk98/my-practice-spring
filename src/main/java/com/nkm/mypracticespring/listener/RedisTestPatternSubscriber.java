package com.nkm.mypracticespring.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisTestPatternSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());

        System.out.println("[RedisTestPatternSubscriber] Received message: " + message + " from channel: " + channel);
    }
}
