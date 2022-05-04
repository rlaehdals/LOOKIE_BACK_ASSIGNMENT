package com.example.lookie.question.service;

import com.example.lookie.question.domain.Question;

import java.util.List;

public interface QuestionService {
    void addQuestion(String ownerEmail, String title);
    void removeQuestion(Long QuestionId);
    void changeQuestionTitle(Long questionId, String changeTitle);
}
