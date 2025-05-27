package com.jlptpracticetest.scoring_service.repo;

import com.jlptpracticetest.scoring_service.model.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExamRepo extends MongoRepository<Exam,String> {
    Optional<Exam> findById(String examId);
}
