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

    private String name;

    private String description;

    private String ownerEmail;

    @OneToMany(mappedBy = "group")
    private List<GroupRequest> groupRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Question> questionList = new ArrayList<>();

    public void addGroupRequest(GroupRequest groupRequest) {
        groupRequestList.add(groupRequest);
    }

    public void addQuestion(Question question){
        this.questionList.add(question);
    }

    private static Group createGroup(String name, String description, String ownerEmail) {
        Group group = new Group();
        group.name = name;
        group.description = description;
        group.ownerEmail = ownerEmail;
        return group;
    }
}
