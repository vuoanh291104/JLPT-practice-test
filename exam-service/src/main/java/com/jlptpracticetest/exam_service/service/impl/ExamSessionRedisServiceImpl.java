package com.jlptpracticetest.exam_service.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlptpracticetest.exam_service.model.ExamSessionRedis;
import com.jlptpracticetest.exam_service.service.ExamSessionRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import static com.jlptpracticetest.exam_service.config.RabbitMQConfig.SCORING_QUEUE;

@Service
@Slf4j
public class ExamSessionRedisServiceImpl implements ExamSessionRedisService {

    @Autowired
    private RedisTemplate<String, ExamSessionRedis> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final  String PREFIX = "exam_session:";
    private final long TTL_SECONDS = 3 * 24 * 60 * 60;
    @Override
    public void createSession(ExamSessionRedis session) {
        redisTemplate.opsForValue().set(PREFIX + session.getSessionId(),session);
        redisTemplate.expire(PREFIX + session.getSessionId(), TTL_SECONDS, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public Optional<ExamSessionRedis> getSession(String sessionId) {
        ExamSessionRedis session = (ExamSessionRedis) redisTemplate.opsForValue().get(PREFIX + sessionId);

        return Optional.ofNullable(session);
    }

    @Override
    public void updateAnswer(String sessionId, String section, int questionIndex, int selectedOptionIndex) {
        ExamSessionRedis session = (ExamSessionRedis) redisTemplate.opsForValue().get(PREFIX + sessionId);
        if(session!=null) {
            session.getAnswers()
                    .computeIfAbsent(section, k-> new HashMap<>())
                    .put(questionIndex,selectedOptionIndex);
            redisTemplate.opsForValue().set(PREFIX + sessionId, session);
            redisTemplate.expire(PREFIX + session.getSessionId(), TTL_SECONDS, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    @Override
    public void submitSession(String sessionId) {
        ExamSessionRedis session = (ExamSessionRedis) redisTemplate.opsForValue().get(PREFIX + sessionId);

        if(session !=null) {
            session.setStatus("SUBMITTED");
            redisTemplate.opsForValue().set(PREFIX + sessionId, session);
            redisTemplate.expire(PREFIX + session.getSessionId(), TTL_SECONDS, java.util.concurrent.TimeUnit.SECONDS);
            // push message to Rabbitmq
            try {
                rabbitTemplate.convertAndSend(SCORING_QUEUE, sessionId);
                log.info("Successfully sent to RabbitMQ. Queue: {}, Payload: {}", SCORING_QUEUE, sessionId);
            } catch (Exception e) {
                log.error("Failed to send message to RabbitMQ", e);
            }
        } else {
            log.warn("Session not found in Redis for ID: {}", sessionId);
        }


    }
}
