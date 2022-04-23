package com.example.hrmanagement.service;

import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record WorkerService(UserRepository repository){
    public Object getAll() {
        return repository.findAll();
    }

    public Status get(UUID id) {
        if (!repository.existsById(id))
            return Status.USER_NOT_FOUND;
        return new Status("Worker with id", true, repository.getById(id));
    }

}
