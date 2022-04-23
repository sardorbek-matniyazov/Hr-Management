package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByUserId(UUID userId);

    List<Salary> findAllByDate(String date);
}
