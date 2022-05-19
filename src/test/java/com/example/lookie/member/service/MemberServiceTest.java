package com.example.lookie.member.service;

import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Member;
import com.example.lookie.member.domain.Role;
import com.example.lookie.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {


    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @BeforeEach
    void init(){
        memberService = new MemberServiceImpl(memberRepository);
    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void successSignup(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.save(member)).thenReturn(member);


        Long memberId = memberService.signup(member.getEmail(), member.getPassword(), Role.ROLE_USER,
                member.getName(), member.getAddress());

        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트")
    void failedSignup(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));


        assertThatThrownBy(() -> memberService.signup(member.getEmail(), member.getPassword(), Role.ROLE_USER,
                member.getName(), member.getAddress())).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("이름 변경 성공 테스트")
    void changeNameSuccessTest(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        memberService.changeName(member.getEmail(), "lee");

        assertThat(member.getName()).isEqualTo("lee");
    }
    @Test
    @DisplayName("이름 변경 실패 테스트")
    void changeNameFailTest(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.findByEmail("hihi")).thenReturn(null);

        assertThatThrownBy(() -> memberService.changeName(member.getEmail(), "lee"))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("패스워드 변경 성공 테스트")
    void changePasswordSuccessTest(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        memberService.changePassword(member.getEmail(), "4321");

        assertThat(member.getPassword()).isEqualTo("4321");
    }
    @Test
    @DisplayName("패스워드 변경 성공 테스트")
    void changePasswordFailTest(){
        Member member = createMember("hi");
        Mockito.when(memberRepository.findByEmail("hihi")).thenReturn(null);

        assertThatThrownBy(() -> memberService.changePassword(member.getEmail(), "4321"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Member createMember(String email){
        return Member.createUserMember(email, "1234","kim",new Address("h1","h1","h1"));
    }
}