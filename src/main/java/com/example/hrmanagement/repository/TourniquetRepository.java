package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.TourniquetCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TourniquetRepository extends JpaRepository<TourniquetCompany, Long> {
    @Query(
            value = "SELECT * FROM tourniquet_company WHERE user_id = ?1 AND (open_in >= ?2 AND open_in < ?3 OR exit >= ?2 AND exit < ?3)",
            nativeQuery = true
    )
    List<TourniquetCompany> findAllByUser_IdAndOpenInLessThanEqualAndExitGreaterThanEqual(UUID id, Timestamp openIn, Timestamp exitIn);
}
