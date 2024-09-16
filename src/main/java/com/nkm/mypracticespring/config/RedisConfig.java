package com.nkm.mypracticespring.config;

import com.nkm.mypracticespring.listener.RedisTestChannelSubscriber;
import com.nkm.mypracticespring.listener.RedisTestPatternSubscriber;
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

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        configuration.setPassword("admin");
        configuration.setDatabase(0);

        // idle: số lượng kết nối nhàn rỗi
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(5);
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(1);

        LettucePoolingClientConfiguration lettucePoolConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();

//        RedisProperties redisProperties = new RedisProperties();
//        redisProperties.setDatabase(Integer.parseInt(System.getenv("REDIS_DB")));
//        redisProperties.setPort(Integer.parseInt(System.getenv("REDIS_PORT")));
//        redisProperties.setPassword(System.getenv("REDIS_PASSWORD"));
//        RedisProperties.Sentinel sentinel = new RedisProperties.Sentinel();
//        sentinel.setMaster(System.getenv("REDIS_MASTER_NAME"));
//        sentinel.setNodes(List.of(System.getenv("REDIS_HOSTS").split(",")));
//        sentinel.setPassword(System.getenv("REDIS_SENTINEL_PASSWORD"));
//        redisProperties.setSentinel(sentinel);

//        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
//        configuration.setDatabase(Integer.parseInt(System.getenv("REDIS_DB")));
//        configuration.setPassword(System.getenv("REDIS_PASSWORD"));
//        configuration.master(System.getenv("REDIS_MASTER_NAME"));
//
//        configuration.setSentinelPassword(System.getenv("REDIS_SENTINEL_PASSWORD"));
//        String[] redisNodes = System.getenv("REDIS_HOSTS").split(",");
//        for (String redisNode : redisNodes) {
//            String host = redisNode.split(":")[0];
//            int port = Integer.parseInt(redisNode.split(":")[1]);
//            RedisNode rdNode = RedisNode.newRedisNode()
//                    .listeningAt(host, port)
//                    .build();
//            configuration.addSentinel(rdNode);
//        }

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

    @Bean(name = "redisTokenPortalV2")
    public RedisTemplate<String, String> redisTokenPortalV2(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
