package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Role;
import com.example.hrmanagement.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getByRole(RoleName roleName);
}
