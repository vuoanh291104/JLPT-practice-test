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
        // TODO: Save
        String userId= session.getUserId();
        String examId = session.getExamId();
        Map<String ,Map<Integer,Integer>> userAnswers = session.getAnswers();

        List<QuestionSet> questionSets = questionSetRepo.findByExamId(examId);


        Map<String, Integer> sectionCorrect = new HashMap<>();
        Map<String, Integer> sectionTotal = new HashMap<>();
        for (QuestionSet qs : questionSets) {
            String section = qs.getSection();
            List<Question> questions = qs.getQuestions();
            int total = questions.size();
            int correctCount = 0;

            Map<Integer, Integer> userSectionAnswer = userAnswers.getOrDefault(section, new HashMap<>());

            for (Question question : questions) {
                Integer userAnswer = userSectionAnswer.get(question.getIndex());
                if (userAnswer != null && userAnswer == question.getCorrectAnswerIndex()) {
                    correctCount++;
                }
            }

            sectionCorrect.put(section, correctCount);
            sectionTotal.put(section, total);
        }
        //TODO: Save to firebase
        int vocabScore = calculateScore(sectionCorrect.getOrDefault("vocabulary", 0), sectionTotal.getOrDefault("vocabulary", 0));
        int readingScore = calculateScore(sectionCorrect.getOrDefault("reading", 0), sectionTotal.getOrDefault("reading", 0));
        int listeningScore = calculateScore(sectionCorrect.getOrDefault("listening", 0), sectionTotal.getOrDefault("listening", 0));
        int totalScore = vocabScore + readingScore + listeningScore;

        log.info("Scoring session {}: vocab={}, reading={}, listening={}, total={}", sessionId, vocabScore, readingScore, listeningScore, totalScore);

        log.info("Scoring session {}: Vocabulary: {}/{} | Reading: {}/{} | Listening: {}/{}",
                sessionId,
                sectionCorrect.getOrDefault("vocabulary", 0), sectionTotal.getOrDefault("vocabulary", 0),
                sectionCorrect.getOrDefault("reading", 0), sectionTotal.getOrDefault("reading", 0),
                sectionCorrect.getOrDefault("listening", 0), sectionTotal.getOrDefault("listening", 0)
        );
    }
    private int calculateScore(int correct, int total) {
        if (total == 0) return 0;
        return (int) Math.round(((double) correct / total) * 60);
    }
}
