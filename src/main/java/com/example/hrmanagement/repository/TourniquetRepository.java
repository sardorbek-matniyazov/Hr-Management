package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.TourniquetCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TourniquetRepository extends JpaRepository<TourniquetCompany, Long> {
    List<TourniquetCompany> findAllByUser_IdAndOpenInLessThanEqualAndExitGreaterThanEqual(UUID id, Timestamp openIn, Timestamp exitIn);
}
