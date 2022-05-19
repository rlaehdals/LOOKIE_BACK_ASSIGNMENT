package com.example.lookie.member.repository;

import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 생성 확인")
    void createMember() {
        /*
        given
         */
        Member userMember = Member.createUserMember("abc@abc.com", "1234", "ABC",
                new Address("서울", "종로", "000006"));
        Member adminMember = Member.createAdminMember("efg@efg.com", "1234", "EFG",
                new Address("서울", "종로", "000006"));

        /*
        when
         */
        Member result1 = memberRepository.save(userMember);
        Member result2 = memberRepository.save(adminMember);

        /*
        then
         */
        assertThat(result1.getName()).isEqualTo(userMember.getName());
        assertThat(result2.getName()).isEqualTo(adminMember.getName());
    }

    @Test
    @DisplayName("멤버 리스트 반환 학인")
    void memberList() {
        /*
        given
         */
        Member userMember = Member.createUserMember("abc@abc.com", "1234", "ABC",
                new Address("서울", "종로", "000006"));
        Member adminMember = Member.createAdminMember("efg.efg.com", "1234", "EFG",
                new Address("서울", "종로", "000006"));
        memberRepository.save(userMember);
        memberRepository.save(adminMember);

        /*
        when
         */
        List<Member> result = memberRepository.findAll();

        /*
        then
         */
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("패스워드 변경 테스트")
    void changePassword(){
        Member userMember = Member.createUserMember("abc@abc.com", "1234", "ABC", new Address("서울", "종로", "000006"));

        memberRepository.save(userMember);
        Member findMember = memberRepository.findByEmail(userMember.getEmail()).get();

        findMember.changePassword("4321");

        Member result = memberRepository.findByEmail(userMember.getEmail()).get();

        assertThat(result.getPassword()).isEqualTo("4321");

    }

    @Test
    @DisplayName("패스워드 변경 테스트")
    void changeName(){
        Member userMember = Member.createUserMember("abc@abc.com", "1234", "ABC", new Address("서울", "종로", "000006"));

        memberRepository.save(userMember);
        Member findMember = memberRepository.findByEmail(userMember.getEmail()).get();

        findMember.changeName("CBA");

        Member result = memberRepository.findByEmail(userMember.getEmail()).get();

        assertThat(result.getName()).isEqualTo("CBA");

    }
}