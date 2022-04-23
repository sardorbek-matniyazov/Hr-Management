package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
