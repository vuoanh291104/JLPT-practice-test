package com.jlptpracticetest.exam_service.service.impl;

import com.jlptpracticetest.exam_service.dto.ExamDTO;
import com.jlptpracticetest.exam_service.repo.ExamRepo;
import com.jlptpracticetest.exam_service.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepo examRepo;

    @Override
    public List<ExamDTO> getExamByLevel(String level) {
        return examRepo.findAllByLevel(level);
    }
}
