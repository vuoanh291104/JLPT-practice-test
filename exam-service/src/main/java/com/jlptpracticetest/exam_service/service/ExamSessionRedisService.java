package com.jlptpracticetest.exam_service.service;

import com.jlptpracticetest.exam_service.model.ExamSessionRedis;

import java.util.Optional;

public interface ExamSessionRedisService {
    void createSession(ExamSessionRedis session);
    Optional<ExamSessionRedis> getSession(String sessionId);
    void updateAnswer(String sessionId, String section, int questionIndex, int selectedOptionIndex);
    void submitSession (String sessionId);
}
