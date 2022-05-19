package com.example.lookie.member.service;

import com.example.lookie.member.domain.Address;
import com.example.lookie.member.domain.Member;
import com.example.lookie.member.domain.Role;
import com.example.lookie.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    // 회원가입
    @Override
    public Long signup(String email, String password, Role role, String name, Address address) {
        // Optional 활용 중복 체크
        memberRepository.findByEmail(email).ifPresent(a -> {
            throw new IllegalArgumentException("이미 있는 이메일입니다. 다른 이메일을 사용해주세요.");
        });

        Member member = CheckRoleAndCreateMember(email, password, role, name, address);

        return memberRepository.save(member).getId();
    }


    // 이름 변경
    @Override
    public void changeName(String email, String name) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 접근입니다.");
        });

        member.changeName(name);
    }

    // 이름 변경
    @Override
    public void changePassword(String email, String password) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new IllegalArgumentException("유효하지 않은 접근입니다.");
        });
        member.changePassword(password);
    }

    // 탈퇴
    @Override
    public void withdrawal(String email) {
        memberRepository.deleteByEmail(email);
    }



    // role 따라서 다른 Member 엔티티 생성
    private Member CheckRoleAndCreateMember(String email, String password, Role role, String name, Address address) {
        if(role==Role.ROLE_ADMIN){
            return Member.createAdminMember(email, password, name, address);
        }else{
            return Member.createUserMember(email, password, name, address);
        }
    }
}
