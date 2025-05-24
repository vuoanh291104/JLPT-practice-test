package com.jlptpracticetest.exam_service.dto;

import java.util.List;

public class QuestionSetDTO {
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<QuestionItemDTO> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(List<QuestionItemDTO> questionItems) {
        this.questionItems = questionItems;
    }

    private String section;
    private List<QuestionItemDTO> questionItems;
}
