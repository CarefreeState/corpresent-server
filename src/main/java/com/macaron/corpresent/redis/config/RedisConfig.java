package com.macaron.corpresent.redis.config;

import com.macaron.corpresent.redis.cache.RedisObjectSerializer;
import com.macaron.corpresent.redis.cache.RedisStringSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
 
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisStringSerializer redisStringSerializer;

    private final RedisObjectSerializer redisObjectSerializer;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(redisStringSerializer);
        template.setValueSerializer(redisObjectSerializer);
        template.setHashKeySerializer(redisStringSerializer);
        template.setHashValueSerializer(redisObjectSerializer);
        template.afterPropertiesSet();
        return template;
    }
}