package com.jlptpracticetest.question_bank_service.service.impl;

import com.jlptpracticetest.question_bank_service.dto.QuestionDTO;
import com.jlptpracticetest.question_bank_service.model.Question;
import com.jlptpracticetest.question_bank_service.model.QuestionSet;
import com.jlptpracticetest.question_bank_service.repo.QuestionSetRepo;
import com.jlptpracticetest.question_bank_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionImpl implements QuestionService {

    @Autowired
    private QuestionSetRepo questionSetRepo;
    @Override
    public List<QuestionDTO> getListQuestionBySection(String examId, String section) {
        Optional<QuestionSet> optionalQuestionSet = questionSetRepo.findByExamIdAndSection(examId, section);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        if (optionalQuestionSet.isPresent()) {
            QuestionSet questionSet = optionalQuestionSet.get();

            for (Question question : questionSet.getQuestions()) {
                QuestionDTO questionDTO = new QuestionDTO(
                        question.getQuestion(),
                        question.getOptions(),
                        question.getCorrectAnswerIndex(),
                        question.getAudioUrl()
                );
                questionDTOS.add(questionDTO);
            }
        }

        return questionDTOS;
    }

    @Override
    public QuestionSet createQuestion(String examId, String section, Question question) {
        Optional<QuestionSet> optionalQuestionSet = questionSetRepo.findByExamIdAndSection(examId,section);
        QuestionSet questionSet;
        if(optionalQuestionSet.isPresent()){
            questionSet = optionalQuestionSet.get();
            question.setIndex(questionSet.getQuestions().size());
            questionSet.getQuestions().add(question);
        }
        else {
            questionSet = new QuestionSet();
            questionSet.setExamId(examId);
            questionSet.setSection(section);
            question.setIndex(0);
            List<Question> questionList = new ArrayList<>();
            questionList.add(question);
            questionSet.setQuestions(questionList);
        }
        return questionSetRepo.save(questionSet);
    }
}
