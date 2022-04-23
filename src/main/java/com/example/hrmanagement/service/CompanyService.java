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
}
