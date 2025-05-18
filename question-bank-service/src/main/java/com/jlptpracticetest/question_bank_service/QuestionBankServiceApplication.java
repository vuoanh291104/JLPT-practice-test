package com.jlptpracticetest.question_bank_service;


import com.jlptpracticetest.exam_service.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
  public class QuestionBankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionBankServiceApplication.class, args);
	}

}
