package com.jlptpracticetest.exam_service.controller;

import com.jlptpracticetest.exam_service.dto.UserAnsweDTO;
import com.jlptpracticetest.exam_service.model.ExamSessionRedis;
import com.jlptpracticetest.exam_service.service.ExamSessionRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/api/exam")
public class ExamAnswerController {
    @Autowired
    private ExamSessionRedisService examSessionRedisService;
    @PostMapping("/answer")
    public ResponseEntity<String> submitAnswer(@RequestBody UserAnsweDTO userAnsweDTO) {
        String sessionId = userAnsweDTO.getSessionId();
        if (examSessionRedisService.getSession(sessionId).isEmpty()) {
            ExamSessionRedis session = new ExamSessionRedis();
            session.setSessionId(sessionId);
            session.setUserId(userAnsweDTO.getUserId());
            session.setExamId(userAnsweDTO.getExamId());
            session.setStatus("IN_PROGRESS");
            session.setStartTime(LocalDate.now().toString());
            session.setAnswers(new HashMap<>());

            examSessionRedisService.createSession(session);
        }

        examSessionRedisService.updateAnswer(
                sessionId,
                userAnsweDTO.getSection(),
                userAnsweDTO.getQuestionIndex(),
                userAnsweDTO.getSelectedOptionIndex()
        );
        return ResponseEntity.ok("answer saved");
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitExam(@RequestParam String sessionId){
        try {
            examSessionRedisService.submitSession(sessionId);
            return ResponseEntity.ok("exam submitted successfully");
        } catch (RuntimeException e) {
            // Log lỗi (nếu muốn)
            return ResponseEntity.status(500).body("Failed to submit exam: " + e.getMessage());
        }
    }
}
