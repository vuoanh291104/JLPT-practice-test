package com.jlptpracticetest.exam_service.service.impl;

import com.jlptpracticetest.exam_service.dto.ExamStartDTO;
import com.jlptpracticetest.exam_service.dto.QuestionItemDTO;
import com.jlptpracticetest.exam_service.dto.QuestionSetDTO;
import com.jlptpracticetest.exam_service.model.QuestionItem;
import com.jlptpracticetest.exam_service.model.QuestionSet;
import com.jlptpracticetest.exam_service.repo.QuestionSetRepo;
import com.jlptpracticetest.exam_service.service.ExamStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExamStartServiceImpl implements ExamStartService {
    @Autowired
    private QuestionSetRepo questionSetRepo;
    @Override
    public ExamStartDTO startExam(String userId, String examId) {
        List<QuestionSet> questionSets = questionSetRepo.findAllByExamId(examId);

        if (questionSets == null || questionSets.isEmpty()) {
            throw new RuntimeException("Không tìm thấy đề thi với examId: " + examId);
        }

        List<QuestionSetDTO> questionSetDTOS = new ArrayList<>();
        for(QuestionSet questionSet: questionSets){{
            QuestionSetDTO questionSetDTO = new QuestionSetDTO();

            List<QuestionItem> questionItems = questionSet.getQuestionItems();
            List<QuestionItemDTO> questionItemDTOS = new ArrayList<>();
            for(QuestionItem questionItem : questionItems){
                QuestionItemDTO questionItemDTO = new QuestionItemDTO();
                questionItemDTO.setIndex(questionItem.getIndex());
                questionItemDTO.setQuestion(questionItem.getQuestion());
                questionItemDTO.setOptions(questionItem.getOptions());
                questionItemDTO.setAudioUrl(questionItem.getAudioUrl());
                questionItemDTOS.add(questionItemDTO);
            }
            questionSetDTO.setSection(questionSet.getSection());
            questionSetDTO.setQuestionItems(questionItemDTOS);
            questionSetDTOS.add(questionSetDTO);
        }}
        String sessionId = UUID.randomUUID().toString();
        String startTime = Instant.now().toString();

        return new ExamStartDTO(sessionId,examId,startTime,questionSetDTOS);
    }
}
