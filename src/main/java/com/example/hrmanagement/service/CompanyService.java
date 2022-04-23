package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Company;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public record CompanyService(CompanyRepository repository) {
    public Status create(Company dto) {
        if (repository.existsByName(dto.getName()))
            return Status.COMPANY_EXIST;
        repository.save(dto.toCompany());
        return Status.SUCCESS;
    }

    public Status update(Long id, Company dto) {
        if (!repository.existsById(id))
            return Status.COMPANY_NOT_FOUND;
        if (repository.existsByName(dto.getName()))
            return Status.COMPANY_EXIST;
        repository.save(
                new Company(id, dto.getName())
        );
        return Status.SUCCESS;
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.COMPANY_NOT_FOUND;
        try {
            repository.deleteById(id);
            return Status.SUCCESS;
        }catch (Exception e){
            return Status.DONT_DELETE_WITH_RELATIONSHIPS;
        }
    }
}
