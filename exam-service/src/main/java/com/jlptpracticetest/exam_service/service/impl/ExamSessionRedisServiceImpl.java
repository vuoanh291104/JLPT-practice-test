package com.jlptpracticetest.exam_service.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlptpracticetest.exam_service.model.ExamSessionRedis;
import com.jlptpracticetest.exam_service.service.ExamSessionRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExamSessionRedisServiceImpl implements ExamSessionRedisService {

    @Autowired
    private RedisTemplate<String, ExamSessionRedis> redisTemplate;
    private final  String PREFIX = "exam_session:";
    @Override
    public void createSession(ExamSessionRedis session) {
        redisTemplate.opsForValue().set(PREFIX + session.getSessionId(),session);
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
        }
    }

    @Override
    public void submitSession(String sessionId) {
        ExamSessionRedis session = (ExamSessionRedis) redisTemplate.opsForValue().get(PREFIX + sessionId);
        if(session !=null) {
            session.setStatus("SUBMITTED");
            redisTemplate.opsForValue().set(PREFIX + sessionId, session);
        }
    }
}
