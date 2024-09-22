package com.nkm.mypracticespring.listener.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisTestChannelSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());

        System.out.println("[RedisTestChannelSubscriber] Received message: " + message + " from channel: " + channel);
    }
}
