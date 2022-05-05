package com.example.lookie.question.service;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.question.domain.Question;
import com.example.lookie.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService{

    private final GroupRepository groupRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void addQuestion(String ownerEmail, String title) {
        Group group = groupRepository.findByOwnerEmail(ownerEmail).orElseThrow(() -> {
            throw new IllegalArgumentException("그룹을 만드시지 않았습니다. ");
        });

        Question question = Question.createQuestion(group, title);
        questionRepository.save(question);
    }

    @Override
    public void removeQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public void changeQuestionTitle(Long questionId, String changeTitle) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 질문 접근입니다. ");
        });
        question.changeTitle(changeTitle);
    }
}
