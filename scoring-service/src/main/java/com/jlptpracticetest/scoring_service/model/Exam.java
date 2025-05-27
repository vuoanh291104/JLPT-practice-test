package com.jlptpracticetest.scoring_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams")
public class Exam {
    @Id
    private String id;
    private String level;
    private  String year;
    private String month;

    public Exam() {
    }

    public Exam(String id, String level, String year, String month) {
        this.id = id;
        this.level = level;
        this.year = year;
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
