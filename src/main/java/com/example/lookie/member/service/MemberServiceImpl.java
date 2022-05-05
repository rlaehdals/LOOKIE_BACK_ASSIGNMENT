package com.example.lookie.member.service;

import com.example.lookie.member.domain.Member;
import com.example.lookie.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        Member newMember = Member.createUserMember(member.getEmail(), member.getPassword(), member.getName(), member.getAddress());
        memberRepository.save(newMember);
        return newMember.getId();
    }

    // 이메일 중복 검사
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMemberList = memberRepository.findByEmail(member.getEmail());
        if (!findMemberList.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
