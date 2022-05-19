package com.example.lookie.question.service;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.question.domain.Question;
import com.example.lookie.question.repository.QuestionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class QuestionServiceTest {

    QuestionService questionService;

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    QuestionRepository questionRepository;

    @BeforeEach
    void init(){
        questionService = new QuestionServiceImpl(groupRepository,questionRepository);
    }

    @Test
    @DisplayName("질문 생성 성공 테스트")
    void createQuestionSuccessTest(){
        Group group = createGroup("hi");
        Question question = createQuestion(group, "title");
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));
        ReflectionTestUtils.setField(question,"id",1L);
        Mockito.when(questionRepository.save(question)).thenReturn(question);
        Mockito.when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));


        questionService.addQuestion(group.getOwnerEmail(),"question1");


        Question findQuestion = questionRepository.findById(question.getId()).get();

        assertThat(findQuestion.getTitle()).isEqualTo("title");

    }

    @Test
    @DisplayName("질문 생성 실패 테스트")
    void createQuestionFailTest(){
        Group group = createGroup("hi");
        Mockito.when(groupRepository.findByOwnerEmail("hi2")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.addQuestion("hi2","title"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Question createQuestion(Group group, String title){
        return Question.createQuestion(group, title);
    }

    private static Group createGroup(String ownerEmail){
        return Group.createGroup("name","description",ownerEmail);
    }

    @Test
    @DisplayName("질문 타이틀 변경 성공 테스트")
    void changeQuestionTitleSuccessTest(){
        Group group = createGroup("hi");
        Question question = createQuestion(group, "title");
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));
        ReflectionTestUtils.setField(question,"id",1L);
        Mockito.when(questionRepository.save(question)).thenReturn(question);
        Mockito.when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        questionService.changeQuestionTitle(question.getId(),"title2");
        Question findQuestion = questionRepository.findById(question.getId()).get();
        assertThat(findQuestion.getTitle()).isEqualTo("title2");
    }
    @Test
    @DisplayName("질문 타이틀 변경 실패 테스트")
    void changeQuestionTitleFailTest(){
        Group group = createGroup("hi");
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));
        Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> questionService.changeQuestionTitle(2L,"title2"))
        .isInstanceOf(IllegalArgumentException.class);
    }
}