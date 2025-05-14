package com.jlptpracticetest.exam_service.service;

import com.jlptpracticetest.exam_service.dto.ExamDTO;

import java.util.List;

public interface ExamService {
    List<ExamDTO> getExamByLevel (String level);
}
