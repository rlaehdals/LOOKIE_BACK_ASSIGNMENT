package com.example.lookie.group.repository;

import com.example.lookie.group.domain.Group;
import com.example.lookie.member.repository.MemberRepository;
import com.example.lookie.question.domain.Question;
import com.example.lookie.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GroupRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("그룹 생성 확인")
    void createGroup() {
        /*
        given
         */
        Group group1 = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group group2 = Group.createGroup("룩희", "루키입니다", "hij@hij.com");

        /*
        when
         */
        Group result1 = groupRepository.save(group1);
        Group result2 = groupRepository.save(group2);


        /*
        then
         */
        assertThat(result1.getName()).isEqualTo(group1.getName());
        assertThat(result2.getName()).isEqualTo(group2.getName());
    }

    @Test
    @DisplayName("그룹 리스트 반환 확인")
    void groupList() {
                /*
        given
         */
        Group group1 = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group group2 = Group.createGroup("룩희", "루키입니다", "hij@hij.com");

        groupRepository.save(group1);
        groupRepository.save(group2);

        /*
        when
         */

        List<Group> result = groupRepository.findAll();

        /*
        then
         */
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findByOwnerEmail 쿼리 테스트")
    void findByOwnerEmailTest() {
        /*
        given
         */
        Group group1 = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group group2 = Group.createGroup("룩희", "루키입니다", "hij@hij.com");

        /*
        when
         */
        groupRepository.save(group1);
        groupRepository.save(group2);

        Group group = groupRepository.findByOwnerEmail(group1.getOwnerEmail()).get();

        /*
        then
         */

        assertThat(group.getName()).isEqualTo("루키");
    }

    @Test
    @DisplayName("Group Name 변경 테스트")
    void changeGroupNameTest() {
        /*
        given
         */
        Group group1 = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group group2 = Group.createGroup("룩희", "루키입니다", "hij@hij.com");

        /*
        when
         */
        groupRepository.save(group1);
        groupRepository.save(group2);

        Group findGroup = groupRepository.findByOwnerEmail(group1.getOwnerEmail()).get();

        findGroup.changeName("lookie");
        Group result = groupRepository.findByOwnerEmail(group1.getOwnerEmail()).get();

        /*
        then
         */
        assertThat(result.getName()).isEqualTo("lookie");
    }

    @Test
    @DisplayName("Group Description 변경 테스트")
    void changeGroupDescriptionTest() {
        /*
        given
         */
        Group group1 = Group.createGroup("루키", "루키입니다", "efg@efg.com");
        Group group2 = Group.createGroup("룩희", "루키입니다", "hij@hij.com");

        /*
        when
         */
        groupRepository.save(group1);
        groupRepository.save(group2);

        Group findGroup = groupRepository.findByOwnerEmail(group1.getOwnerEmail()).get();

        findGroup.changeDescription("lookie 입니다.");
        Group result = groupRepository.findByOwnerEmail(group1.getOwnerEmail()).get();

        /*
        then
         */
        assertThat(result.getDescription()).isEqualTo(findGroup.getDescription());
    }

    @Test
    @DisplayName("그룹 질문 리스트 반환")
    void groupQuestionList(){
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
        Group result = groupRepository.findByName("루키").get();
        /*
        then
        */
        assertThat(result.getQuestionList().size()).isEqualTo(2);
    }
}