package com.jlptpracticetest.scoring_service.consumer;

import com.jlptpracticetest.scoring_service.service.ScoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScoringCosumer {

    @Autowired
    private  ScoringService scoringService;

    @RabbitListener(queues = "scoring_queue")
    public void receiveMessage(String sessionId){
        log.info("Received sessionId from queue: {}", sessionId);
        try {
            scoringService.scoreExam(sessionId);
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}
