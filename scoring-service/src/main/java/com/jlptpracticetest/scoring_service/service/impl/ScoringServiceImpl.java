package com.jlptpracticetest.scoring_service.service.impl;

import com.jlptpracticetest.scoring_service.model.ExamSessionRedis;
import com.jlptpracticetest.scoring_service.model.Question;
import com.jlptpracticetest.scoring_service.model.QuestionSet;
import com.jlptpracticetest.scoring_service.repo.QuestionSetRepo;
import com.jlptpracticetest.scoring_service.service.ScoringService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ScoringServiceImpl implements ScoringService {
    @Autowired
    RedisTemplate<String, ExamSessionRedis> redisTemplate;
    @Autowired
    private QuestionSetRepo questionSetRepo;
    private final String PREFIX = "exam_session:";
    @Override
    public void scoreExam(String sessionId) {
        ExamSessionRedis session = redisTemplate.opsForValue().get(PREFIX + sessionId);
        if(session == null){
            throw new RuntimeException("Exam session not found in Redis for sessionId: " + sessionId);

        }

        String examId = session.getExamId();
        Map<String ,Map<Integer,Integer>> userAnswers = session.getAnswers();

        List<QuestionSet> questionSets = questionSetRepo.findByExamId(examId);

        int totalCorrect = 0;
        int totalQuestions = 0;

        for(QuestionSet qs : questionSets){
            String section = qs.getSection();
            Map<Integer, Integer> userSectionAnswer = userAnswers.getOrDefault(section, new HashMap<>());
            for (Question question : qs.getQuestions()){
                totalQuestions++;

                Integer userAnswer = userSectionAnswer.get(question.getIndex());
                if (userAnswer != null && userAnswer == question.getCorrectAnswerIndex()) {
                    totalCorrect++;
                }
            }
        }

        log.info("cham diem" + sessionId + " la" + totalCorrect);
    }
}
