package com.jlptpracticetest.scoring_service.repo;

import com.jlptpracticetest.scoring_service.model.QuestionSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionSetRepo extends MongoRepository<QuestionSet, String> {
    List<QuestionSet> findByExamId(String examId);
}
