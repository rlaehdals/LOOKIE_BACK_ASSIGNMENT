package com.example.lookie.grouprequest.repository;

import com.example.lookie.grouprequest.domain.GroupRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRequestRepository extends JpaRepository<GroupRequest, Long> {

    @Query("select gr from GroupRequest gr join fetch gr.member join fetch gr.group where gr.member.email=:memberEmail and gr.group.name=:groupName")
    Optional<GroupRequest> findByMemberEmailAndGroupName(String memberEmail, String groupName);

//    @EntityGraph(attributePaths = "group")
    @Query("select gr from GroupRequest gr join fetch gr.group where gr.id=:id")
    Optional<GroupRequest> findFetchJoinById(Long id);
}
