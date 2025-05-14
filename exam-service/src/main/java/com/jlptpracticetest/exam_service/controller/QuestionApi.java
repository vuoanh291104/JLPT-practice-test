package com.jlptpracticetest.exam_service.controller;

import com.jlptpracticetest.exam_service.dto.ExamStartDTO;
import com.jlptpracticetest.exam_service.service.ExamStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class QuestionApi {
    @Autowired
    private ExamStartService examStartService;
    @GetMapping("/start")
    public ExamStartDTO startExam(@RequestParam String userId, @RequestParam String examId){
        return examStartService.startExam(userId,examId);
    }
}
