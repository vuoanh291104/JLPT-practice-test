package com.jlptpracticetest.exam_service.dto;

import java.util.List;

public class ExamStartDTO {
    private String sessionId;
    private String examId;
    private String startTime;
    private List<QuestionSetDTO> questionSets;

    public ExamStartDTO(String sessionId, String examId, String startTime, List<QuestionSetDTO> questionSets) {
        this.sessionId = sessionId;
        this.examId = examId;
        this.startTime = startTime;
        this.questionSets = questionSets;
    }

    public ExamStartDTO() {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<QuestionSetDTO> getQuestionSets() {
        return questionSets;
    }

    public void setQuestionSets(List<QuestionSetDTO> questionSets) {
        this.questionSets = questionSets;
    }



}
