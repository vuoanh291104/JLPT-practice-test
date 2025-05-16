package com.jlptpracticetest.exam_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlptpracticetest.exam_service.model.ExamSessionRedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, ExamSessionRedis> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, ExamSessionRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Sử dụng Jackson2JsonRedisSerializer cho ExamSessionRedis
        Jackson2JsonRedisSerializer<ExamSessionRedis> serializer =
                new Jackson2JsonRedisSerializer<>(ExamSessionRedis.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
