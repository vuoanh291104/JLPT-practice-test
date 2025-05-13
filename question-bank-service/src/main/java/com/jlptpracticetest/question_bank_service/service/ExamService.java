package com.jlptpracticetest.question_bank_service.service;

import com.jlptpracticetest.question_bank_service.dto.ExamDTO;
import com.jlptpracticetest.question_bank_service.model.Exam;

import java.util.List;

public interface ExamService {
    List<ExamDTO> getExamsByLevel(String level);
    Exam createExam (Exam exam);
    Exam updateExam(String id, Exam exam);
    void deleteExam(String id);

}
