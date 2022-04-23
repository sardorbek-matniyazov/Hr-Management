package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.payload.ManagerDto;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.repository.CompanyRepository;
import com.example.hrmanagement.repository.RoleRepository;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.UUID;

@Service
public record WorkerService(UserRepository repository,
                            CompanyRepository companyRepository,
                            PasswordEncoder encoder,
                            TourniquetRepository tourniquetRepository,
                            RoleRepository roleRepository){
    public Object getAll() {
        return repository.findAll();
    }


    public Status get(UUID id, Timestamp openIn, Timestamp exitIn) {
        if (!repository.existsById(id))
            return Status.USER_NOT_FOUND;
        return new Status("here",
                true,
                tourniquetRepository.findAllByUser_IdAndOpenInLessThanEqualAndExitGreaterThanEqual(
                        id,
                        openIn,
                        exitIn
                )
        );
    }

    public Status update(UUID id, ManagerDto dto) {
        if (!repository.existsById(id))
            return Status.NOT_FOUND;
        if (repository.existsByUsername(dto.getUsername())) {
            return Status.USERNAME_ALREADY_EXISTS;
        }
        if (repository.existsByEmail(dto.getEmail())) {
            return Status.EMAIL_ALREADY_EXISTS;
        }
        if (!companyRepository.existsById(dto.getCompanyId())) {
            return Status.COMPANY_NOT_FOUND;
        }
        User user = dto.toUser();
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setId(id);

        switch (dto.getRole()) {
            case "MANAGER":
                user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.MANAGER)));
                break;
            case "HR_MANAGER":
                user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.HR_MANAGER)));
                break;
            case "WORKER":
                user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.WORKER)));
                break;
            default:
                return Status.INVALID_ROLE;
        }
        repository.save(user);
        return Status.SUCCESS;
    }

    public Status delete(UUID id) {
        if (!repository.existsById(id))
            return Status.NOT_FOUND;
        try {
            repository.deleteById(id);
            return Status.SUCCESS;
        }catch (Exception e){
            return Status.DONT_DELETE_WITH_RELATIONSHIPS;
        }
    }

}
