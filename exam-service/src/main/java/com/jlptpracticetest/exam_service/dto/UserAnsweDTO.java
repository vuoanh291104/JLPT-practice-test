package com.jlptpracticetest.exam_service.dto;

public class UserAnsweDTO {
    private String sessionId;
    private String examId;
    private String userId;
    private String section;
    private int questionIndex;
    private int selectedOptionIndex;

    public UserAnsweDTO(String sessionId, String examId, String userId, String section, int questionIndex, int selectedOptionIndex) {
        this.sessionId = sessionId;
        this.examId = examId;
        this.userId = userId;
        this.section = section;
        this.questionIndex = questionIndex;
        this.selectedOptionIndex = selectedOptionIndex;
    }

    public UserAnsweDTO() {
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }
}
