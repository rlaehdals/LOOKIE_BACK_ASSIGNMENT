package com.example.lookie.question.repository;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Member;
import com.example.lookie.question.domain.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    GroupRepository groupRepository;

    @Test
    @DisplayName("질문 생성 확인")
    void createQuestion() {
        /*
        given
         */
        Group group = Group.createGroup("루키", "루키입니다", "abc@abc.com");
        groupRepository.save(group);

        Question question1 = Question.createQuestion(group, "질문1");
        Question question2 = Question.createQuestion(group, "질문2");

        /*
        when
         */
        Question result1 = questionRepository.save(question1);
        Question result2 = questionRepository.save(question2);

        /*
        then
         */
        assertThat(result1.getTitle()).isEqualTo(question1.getTitle());
        assertThat(result2.getTitle()).isEqualTo(question2.getTitle());
    }

    @Test
    @DisplayName("질문 리스트 반환 확인")
    void questionList() {
        /*
        given
         */
        Group group = Group.createGroup("루키", "루키입니다", "abc@abc.com");
        Group groupResult = groupRepository.save(group);

        Question question1 = Question.createQuestion(groupResult, "질문1");
        Question question2 = Question.createQuestion(groupResult, "질문2");

        questionRepository.save(question1);
        questionRepository.save(question2);

        /*
        when
         */
        List<Question> result = questionRepository.findAll();

        /*
        then
        */
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("질문 타이틀 변경 테스트")
    void changeQuestionTitle() {
        /*
        given
         */
        Group group = Group.createGroup("루키", "루키입니다", "abc@abc.com");
        groupRepository.save(group);

        Question question = Question.createQuestion(group, "질문1");

        /*
        when
         */
        Question saveQuestion = questionRepository.save(question);

        Question findQuestion = questionRepository.findById(saveQuestion.getId()).get();

        findQuestion.changeTitle("질문2");

        Question result = questionRepository.findById(saveQuestion.getId()).get();

        assertThat(result.getTitle()).isEqualTo("질문2");
    }
}