package com.example.hrmanagement.service;

import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public record WorkerService(UserRepository repository,
                            TourniquetRepository tourniquetRepository){
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
}
