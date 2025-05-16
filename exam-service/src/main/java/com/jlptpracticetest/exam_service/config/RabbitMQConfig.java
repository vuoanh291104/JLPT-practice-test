package com.jlptpracticetest.exam_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String SCORING_QUEUE = "scoring_queue";
    @Bean
    public Queue scoringQueue() {
        return  new Queue(SCORING_QUEUE,true);
    }
}
