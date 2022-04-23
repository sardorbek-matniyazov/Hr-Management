package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Work;
import com.example.hrmanagement.entity.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WorkRepository extends JpaRepository<Work, UUID> {
    List<Work> findAllByStatusNameAndWorkerId(StatusName statusName, UUID worker_id);

    @Query(
            value = "SELECT * FROM work WHERE expired_date >= finished_date",
            nativeQuery = true
    )
    List<Work> findFinishedWorks();

    @Query(
            value = "SELECT * FROM work WHERE expired_date < finished_date OR finished_date = null",
            nativeQuery = true
    )
    List<Work> findNonFinishedWorks();
}
