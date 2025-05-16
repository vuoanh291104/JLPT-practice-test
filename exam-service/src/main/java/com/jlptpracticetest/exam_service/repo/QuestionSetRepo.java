package com.jlptpracticetest.exam_service.repo;

import com.jlptpracticetest.exam_service.model.QuestionSet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionSetRepo extends MongoRepository<QuestionSet,String> {

    List<QuestionSet> findAllByExamId(String examId);
}
