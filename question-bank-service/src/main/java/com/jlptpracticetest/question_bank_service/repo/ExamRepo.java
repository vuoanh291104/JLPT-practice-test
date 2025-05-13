package com.jlptpracticetest.question_bank_service.repo;

import com.jlptpracticetest.question_bank_service.model.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExamRepo extends MongoRepository<Exam,String> {
    List<Exam> findAllByLevel(String level);
}
