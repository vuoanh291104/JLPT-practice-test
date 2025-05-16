package com.jlptpracticetest.exam_service.service;

import com.jlptpracticetest.exam_service.dto.ExamStartDTO;

public interface ExamStartService {
    ExamStartDTO startExam(String userId, String examId);
}
