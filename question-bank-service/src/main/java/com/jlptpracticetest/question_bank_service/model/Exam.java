package com.jlptpracticetest.question_bank_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "exams")

public class Exam {
    @Id
    private String id;
    private String level;
    private int year;
    private int month;
    private Instant createdAt;
    private Instant updatedAt;

    public Exam(String id, String level, int year, int month, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.level = level;
        this.year = year;
        this.month = month;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Exam() {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
