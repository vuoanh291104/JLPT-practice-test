package com.jlptpracticetest.question_bank_service.service;

import com.jlptpracticetest.question_bank_service.dto.QuestionDTO;
import com.jlptpracticetest.question_bank_service.model.Question;
import com.jlptpracticetest.question_bank_service.model.QuestionSet;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getListQuestionBySection(String examId, String section);
    QuestionSet createQuestion(String examId, String section, Question question);

}
