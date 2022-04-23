package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.TourniquetCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourniquetRepository extends JpaRepository<TourniquetCompany, Long> {
}
