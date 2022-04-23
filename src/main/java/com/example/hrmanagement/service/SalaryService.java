package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Salary;
import com.example.hrmanagement.payload.SalaryDto;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.repository.SalaryRepository;
import com.example.hrmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public record SalaryService(SalaryRepository repository,
                            UserRepository userRepository) {
    public Status create(SalaryDto dto) {
        if (!userRepository.existsById(dto.getUserId()))
            return Status.USER_NOT_FOUND;
        repository.save(
                new Salary(
                        userRepository.getById(dto.getUserId()),
                        dto.getSalary(),
                        new SimpleDateFormat("dd-MM-yyyy").format(dto.getDate())
                )
        );
        return Status.SUCCESS;
    }

    public List<Salary> getAll(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    public List<Salary> getAllByDate(String date) {
        return repository.findAllByDate(date);
    }
}
