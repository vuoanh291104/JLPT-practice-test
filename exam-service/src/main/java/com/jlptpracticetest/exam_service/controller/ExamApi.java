package com.jlptpracticetest.exam_service.controller;

import com.jlptpracticetest.exam_service.dto.ExamDTO;
import com.jlptpracticetest.exam_service.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
public class ExamApi {
    @Autowired
    private ExamService examService;
    @GetMapping("")
    public List<ExamDTO> getExamByLevel (@RequestParam String level){
        return examService.getExamByLevel(level);
    }
}
