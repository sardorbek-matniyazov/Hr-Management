package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Work;
import com.example.hrmanagement.entity.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkRepository extends JpaRepository<Work, UUID> {
    List<Work> findAllByStatusNameAndWorkerId(StatusName statusName, UUID worker_id);
}
