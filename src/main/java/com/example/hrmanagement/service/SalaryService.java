package com.example.hrmanagement.service;

import com.example.hrmanagement.repository.SalaryRepository;
import org.springframework.stereotype.Service;

@Service
public record SalaryService(SalaryRepository repository) {
}
