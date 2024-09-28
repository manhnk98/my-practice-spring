package com.nkm.mypracticespring.config;

import com.nkm.mypracticespring.listener.redis.RedisTestChannelSubscriber;
import com.nkm.mypracticespring.listener.redis.RedisTestPatternSubscriber;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Log4j2
@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setPassword("admin");
        configuration.setDatabase(0);

        SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(Duration.ofSeconds(1))  // Thời gian chờ kết nối
                .build();

        ClientOptions clientOptions = ClientOptions.builder()
                .socketOptions(socketOptions)
                .autoReconnect(true)
                .build();

        // idle: số lượng kết nối nhàn rỗi
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(3);
        poolConfig.setMaxTotal(5);
        poolConfig.setMinIdle(1);

        LettucePoolingClientConfiguration lettucePoolConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(1))
                .build();

        return new LettuceConnectionFactory(configuration, lettucePoolConfig);
    }

    @Autowired
    RedisTestChannelSubscriber redisTestChannelSubscriber;

    @Autowired
    RedisTestPatternSubscriber redisTestPatternSubscriber;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());

        container.addMessageListener(redisTestChannelSubscriber, new ChannelTopic("test_subscriber"));
        container.addMessageListener(redisTestPatternSubscriber, new PatternTopic("test_*"));

        return container;
    }

    @Bean(name = "sessionManagerRedis")
    public RedisTemplate<String, String> sessionManagerRedis(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

}
