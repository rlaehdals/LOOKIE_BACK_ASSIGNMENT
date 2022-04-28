package com.example.lookie.group.domain;

import com.example.lookie.grouprequest.domain.GroupRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_email")
    private String ownerEmail;

    @OneToMany(mappedBy = "group", orphanRemoval = true)
    private List<GroupRequest> groupRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "group",orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();


    // 연관관계 메서드
    public void addGroupRequest(GroupRequest groupRequest) {
        groupRequestList.add(groupRequest);
    }

    public void addQuestion(Question question){
        this.questionList.add(question);
    }


    //생성 메서드
    private static Group createGroup(String name, String description, String ownerEmail) {
        Group group = new Group();
        group.name = name;
        group.description = description;
        group.ownerEmail = ownerEmail;
        return group;
    }
}
