package com.jlptpracticetest.question_bank_service.service.impl;

import com.jlptpracticetest.question_bank_service.dto.ExamDTO;
import com.jlptpracticetest.question_bank_service.model.Exam;
import com.jlptpracticetest.question_bank_service.repo.ExamRepo;
import com.jlptpracticetest.question_bank_service.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepo examRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByLevel(String level) {

        return examRepo.findAllByLevel(level)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Exam createExam(Exam exam) {
        exam.setCreatedAt(Instant.now());
        exam.setUpdatedAt(Instant.now());
        return examRepo.save(exam);
    }

    @Override
    public Exam updateExam(String id, Exam exam) {
        Exam existingExam = examRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Not found the exam"));
        existingExam.setLevel(exam.getLevel());
        existingExam.setYear(exam.getYear());
        existingExam.setMonth(exam.getMonth());
        existingExam.setUpdatedAt(Instant.now());
        return examRepo.save(existingExam);
    }

    @Override
    public void deleteExam(String id) {
        examRepo.deleteById(id);
    }
    private ExamDTO convertDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setYear(exam.getYear());
        dto.setMonth(exam.getMonth());
        dto.setLevel(exam.getLevel());

        return dto;
    }
}
