package com.example.lookie.group.service;

import com.example.lookie.group.domain.Group;
import com.example.lookie.question.domain.Question;

import java.util.List;

public interface GroupService {

    Long createGroup(String name, String description, String ownerEmail);

    Group findByNameGroup(String name);

    List<Group> findAllGroup();

    void deleteGroup(String name);

    void changeGroupName(String changeName, String ownerEmail);

    void changeDescription(String changeDescription, String ownerEmail);

    List<Question> findQuestionList(String name);
}
