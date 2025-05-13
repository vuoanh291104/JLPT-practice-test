package com.jlptpracticetest.question_bank_service.controller;

import com.jlptpracticetest.question_bank_service.dto.QuestionDTO;
import com.jlptpracticetest.question_bank_service.model.Question;
import com.jlptpracticetest.question_bank_service.model.QuestionSet;
import com.jlptpracticetest.question_bank_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionApi {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public ResponseEntity<List<QuestionDTO>> getQuestionOfSection(@RequestParam String examId, @RequestParam String section){
        List<QuestionDTO> questions = questionService.getListQuestionBySection(examId, section);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<QuestionSet> createQuestionSet(
            @RequestParam String examId,
            @RequestParam String section,
            @RequestBody Question question
    ){
        QuestionSet savedSet = questionService.createQuestion(examId,section,question);
        return ResponseEntity.ok(savedSet);
    }

}
