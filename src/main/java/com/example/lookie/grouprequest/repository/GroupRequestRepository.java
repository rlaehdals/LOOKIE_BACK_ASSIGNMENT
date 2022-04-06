package com.example.lookie.grouprequest.repository;

import com.example.lookie.grouprequest.domain.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRequestRepository extends JpaRepository<GroupRequest, Long> {
}
