package com.example.lookie.group.repository;

import com.example.lookie.group.domain.Group;
import com.example.lookie.member.repository.MemberRepository;
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
}