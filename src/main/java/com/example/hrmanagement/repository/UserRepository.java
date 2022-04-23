package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    User getByUsername(String username);
}
