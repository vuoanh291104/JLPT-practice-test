package com.jlptpracticetest.question_bank_service.controller;

import com.jlptpracticetest.question_bank_service.dto.ExamDTO;
import com.jlptpracticetest.question_bank_service.model.Exam;
import com.jlptpracticetest.question_bank_service.service.ExamService;
import jakarta.ws.rs.PUT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class ExamApi {
    @Autowired
    private ExamService examService;

    @GetMapping("/level/{level}")
    public ResponseEntity<List<ExamDTO>> getExamsByLevel(
            @PathVariable String level
    ){
        return ResponseEntity.ok(examService.getExamsByLevel(level));
    }

    @PostMapping("/new")
    public ResponseEntity<Exam> createNewExam(
            @RequestBody Exam exam
    ){
        return new ResponseEntity<>(examService.createExam(exam), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(
            @PathVariable String id,
            @RequestBody Exam exam
    ){
        return ResponseEntity.ok(examService.updateExam(id,exam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam (
            @PathVariable String id
    ){
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
