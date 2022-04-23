package com.example.hrmanagement;

import com.example.hrmanagement.entity.TourniquetCompany;
import com.example.hrmanagement.repository.TourniquetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class HrManagementApplicationTests {

    @Autowired
    TourniquetRepository tourniquetRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void tourniquetMethodCheck() {
        List<TourniquetCompany> list =
                tourniquetRepository.findAllByUser_IdAndOpenInLessThanEqualAndExitGreaterThanEqual(UUID.randomUUID(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        Timestamp.valueOf(LocalDateTime.now().plusDays(6))
                );
        list.forEach(System.out::println);
    }
}
