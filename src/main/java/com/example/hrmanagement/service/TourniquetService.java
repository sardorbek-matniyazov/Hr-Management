package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.TourniquetCompany;
import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.payload.TourniquetDto;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public record TourniquetService (TourniquetRepository repository,
                                 UserRepository userRepository){
    public Status create(TourniquetDto dto) {
        if (!userRepository.existsById(dto.getUserId()))
            return Status.USER_NOT_FOUND;
        User user = userRepository.getById(dto.getUserId());
        TourniquetCompany tourniquet = new TourniquetCompany(
                user.getCompany(),
                user
        );
        if (dto.isOpenIn()){
            tourniquet.setOpenIn(Timestamp.valueOf(LocalDateTime.now()));
        }else{
            tourniquet.setExitIn(Timestamp.valueOf(LocalDateTime.now()));
        }
        repository.save(tourniquet);
        return Status.SUCCESS;
    }
}
