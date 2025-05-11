package com.jlptpracticetest.question_bank_service.repo;

import com.jlptpracticetest.question_bank_service.model.QuestionSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionSetRepo extends MongoRepository<QuestionSet, String> {
    List<QuestionSet> findByExamId(String examId);
    Optional<QuestionSet> findByExamIdAndSection(String examId, String section);

    void deleteByExamId(String examId);

}
