package com.example.lookie.member.domain;


import com.example.lookie.grouprequest.domain.GroupRequest;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<GroupRequest> groupRequestList = new ArrayList<>();


    // 연관관계 메서드
    public void addGroupRequest(GroupRequest groupRequest){
        this.groupRequestList.add(groupRequest);
    }


    // User 를 생성하는 메서드
    public static Member createUserMember(String email, String password, String name, Address address){
        Member member = new Member();
        member.name=name;
        member.email=email;
        member.password=password;
        member.role=Role.ROLE_USER;
        member.address=address;
        return member;
    }


    // 동아리 관리자를 생성하는 메서드
    public static Member createAdminMember(String email, String password, String name, Address address){
        Member member = new Member();
        member.name=name;
        member.email=email;
        member.password=password;
        member.role=Role.ROLE_ADMIN;
        member.address=address;
        return member;
    }

    // 비즈니스 로직

    public void changeName(String name) {
        this.name=name;
    }

    public void changePassword(String password) {
        this.password=password;
    }

    @Override
    public boolean equals(Object obj) {
        Member obj1 = (Member) obj;
        return this.email.equals(obj1.email);
    }
}
