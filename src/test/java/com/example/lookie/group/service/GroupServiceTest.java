package com.example.lookie.group.service;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.question.domain.Question;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class GroupServiceTest {


    GroupService groupService;

    @MockBean
    GroupRepository groupRepository;

    @BeforeEach
    void init(){
        groupService = new GroupServiceImpl(groupRepository);
    }

    @Test
    @DisplayName("그룹 생성 성공 테스트")
    void createGroupSuccessTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.save(group)).thenReturn(group);
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.empty());
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.empty());
        Mockito.when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        Long groupId = groupService.createGroup(group.getName(), group.getDescription(), group.getOwnerEmail());

        Group result = groupRepository.findById(groupId).get();

        assertThat(result.getName()).isEqualTo("lookie");
    }

    @Test
    @DisplayName("그룹 생성 실패 테스트 by 이미 존재하는 그룹 이름")
    void createGroupFailByDuplicateNameTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));

        assertThatThrownBy(() -> groupService.createGroup(group.getName(), group.getDescription()
                , group.getOwnerEmail())).hasMessage("이미 존재하는 그룹 이름입니다. ");


    }

    @Test
    @DisplayName("그룹 생성 실패 테스트 by 이미 그룹을 만듬")
    void createGroupFailByAlreadyCreateGroupTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.empty());
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));
        assertThatThrownBy(() -> groupService.createGroup(group.getName(), group.getDescription()
                , group.getOwnerEmail())).hasMessage("이미 그룹을 한 번 만드셨습니다.");
    }

    @Test
    @DisplayName("그룹 리스트 받기")
    void findAllGroupTest(){
        Group group1 = createGroup("lookie1", "abcd@com1");
        Group group2 = createGroup("lookie2", "abcd@com2");

        Mockito.when(groupRepository.findAll()).thenReturn(List.of(group1,group2));

        List<Group> result = groupService.findAllGroup();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("그룹 이름 바꾸기 성공 테스트 ")
    void changeGroupNameSuccessTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));
        groupService.changeGroupName("lookie22",group.getOwnerEmail());

        Group result = groupRepository.findByOwnerEmail(group.getOwnerEmail()).get();
        assertThat(result.getName()).isEqualTo("lookie22");
        assertThat(result.getOwnerEmail()).isEqualTo(group.getOwnerEmail());
    }

    @Test
    @DisplayName("그룹 이름 바꾸기 실패 테스트 by 이미 존재하는 이름 ")
    void changeGroupNameFailByDuplicateNameTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));

        assertThatThrownBy(()-> groupService.changeGroupName(group.getName(),group.getOwnerEmail()))
        .hasMessage("바꾸려는 이름이 존재하는 이름입니다.");

    }

    @Test
    @DisplayName("그룹 이름 바꾸기 실패 테스트 by 유저가 만든 그룹이 없음")
    void changeGroupNameFailByNonGroupTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(()-> groupService.changeGroupName(group.getName(),group.getOwnerEmail()))
                .hasMessage("유저가 만든 그룹은 없습니다.");
    }

    @Test
    @DisplayName("그룹 설명 바꾸기 성공 테스트")
    void changeDescriptionSuccessTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.of(group));

        groupService.changeDescription("change",group.getOwnerEmail());

        Group result = groupRepository.findByOwnerEmail(group.getOwnerEmail()).get();

        assertThat(result.getDescription()).isEqualTo("change");

    }
    @Test
    @DisplayName("그룹 설명 바꾸기 실패 테스트 by 만든 그룹이 없음")
    void changeDescriptionFailByNonGroupTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByOwnerEmail(group.getOwnerEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(()->groupService.changeDescription("change",group.getOwnerEmail()))
        .hasMessage("유저가 만든 그룹은 없습니다.");
    }

    @Test
    @DisplayName("그룹 이름으로 그룹 찾기 성공 테스트")
    void findByGroupNameSuccessTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));

        Group result = groupService.findByNameGroup(group.getName());

        assertThat(result.getOwnerEmail()).isEqualTo(group.getOwnerEmail());
    }

    @Test
    @DisplayName("그룹 이름으로 그룹 찾기 실패 테스트 by 그룹 이름이 없음")
    void findByGroupNameFailByNonNameTest(){
        Group group = createGroup("lookie", "abcd@com");
        ReflectionTestUtils.setField(group,"id",1L);
        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.empty());


        assertThatThrownBy(() -> groupService.findByNameGroup("lookie2"))
                .hasMessage("없는 그룹 이름입니다. ");
    }

    @Test
    @DisplayName("그룹에 관련된 질문 리스트 조회 성공 테스트")
    void findQuestionListSuccessTest(){
        Group group = createGroup("lookie", "abcd@com");
        Question question1 = Question.createQuestion(group, "question1");
        Question question2 = Question.createQuestion(group, "question2");

        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.of(group));

        List<Question> questionList = groupService.findQuestionList(group.getName());

        assertThat(questionList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("그룹에 관련된 질문 리스트 조회 실패 테스트 by 만든 그룹이 없음")
    void findQuestionListFailByNonGroupTest(){
        Group group = createGroup("lookie", "abcd@com");
        Question question1 = Question.createQuestion(group, "question1");
        Question question2 = Question.createQuestion(group, "question2");

        Mockito.when(groupRepository.findByName(group.getName())).thenReturn(Optional.empty());


        assertThatThrownBy(() -> groupService.findQuestionList("lookie2")).hasMessage("없는 그룹 이름입니다.");

    }


    private static Group createGroup(String name, String ownerEmail){
        return Group.createGroup(name,"hi",ownerEmail);
    }
}