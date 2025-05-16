package com.jlptpracticetest.exam_service.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ExamSessionRedis implements Serializable {
    private String sessionId;
    private String examId;
    private String userId;
    private String startTime;
    private String status;
    private Map<String, Map<Integer,Integer>> answers = new HashMap<>();

    public ExamSessionRedis(String sessionId, String examId, String userId, String startTime, String status, Map<String, Map<Integer, Integer>> answers) {
        this.sessionId = sessionId;
        this.examId = examId;
        this.userId = userId;
        this.startTime = startTime;
        this.status = status;
        this.answers = answers;
    }

    public ExamSessionRedis() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Map<Integer, Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Map<Integer, Integer>> answers) {
        this.answers = answers;
    }
}
