package com.jlptpracticetest.exam_service.repo;

import com.jlptpracticetest.exam_service.dto.ExamDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExamRepo extends MongoRepository<ExamDTO, String> {
    List<ExamDTO> findAllByLevel(String level);
}
