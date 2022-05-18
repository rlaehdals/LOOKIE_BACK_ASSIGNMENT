package com.example.lookie.group.service;

import com.example.lookie.group.domain.Group;
import com.example.lookie.group.repository.GroupRepository;
import com.example.lookie.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    @Override
    public Long createGroup(String name, String description, String ownerEmail) {

        groupRepository.findByName(name).ifPresent(a -> {
            throw new IllegalArgumentException("이미 존재하는 그룹 이름입니다. ");
        });

        groupRepository.findByOwnerEmail(ownerEmail).ifPresent(a -> {
            throw new IllegalArgumentException("이미 그룹을 한 번 만드셨습니다.");
        });

        Group group = Group.createGroup(name, description, ownerEmail);

        return groupRepository.save(group).getId();
    }

    @Transactional
    @Override
    public void changeGroupName(String changeName, String ownerEmail) {
        Group group = groupRepository.findByOwnerEmail(ownerEmail).orElseThrow(() -> {
            throw new IllegalArgumentException("유저가 만든 그룹은 없습니다.");
        });

        groupRepository.findByName(changeName).ifPresent(a -> {
            throw new IllegalArgumentException("바꾸려는 이름이 존재하는 이름입니다.");
        });

        group.changeName(changeName);
    }

    @Transactional
    @Override
    public void changeDescription(String changeDescription, String ownerEmail) {
        Group group = groupRepository.findByOwnerEmail(ownerEmail).orElseThrow(() -> {
            throw new IllegalArgumentException("유저가 만든 그룹은 없습니다.");
        });
        group.changeDescription(changeDescription);
    }

    @Override
    public List<Question> findQuestionList(String name) {
        Group group = groupRepository.findByName(name).orElseThrow(() -> {
            throw new IllegalArgumentException("없는 그룹 이름입니다.");
        });
        return group.getQuestionList();
    }

    @Override
    public Group findByNameGroup(String name) {
        return groupRepository.findByName(name).orElseThrow(() -> {
            throw new IllegalArgumentException("없는 그룹 이름입니다. ");
        });
    }

    @Override
    public List<Group> findAllGroup() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteGroup(String ownerEmail) {
        groupRepository.deleteByOwnerEmail(ownerEmail);
    }
}
