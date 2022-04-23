package com.example.hrmanagement;

import com.example.hrmanagement.entity.Salary;
import com.example.hrmanagement.entity.TourniquetCompany;
import com.example.hrmanagement.entity.Work;
import com.example.hrmanagement.repository.SalaryRepository;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.WorkRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Slf4j
class HrManagementApplicationTests {

    @Autowired
    TourniquetRepository tourniquetRepository;

    @Autowired
    WorkRepository workRepository;

   @Autowired
   SalaryRepository salaryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void tourniquetMethodCheck() {
        List<TourniquetCompany> list =
                tourniquetRepository.findAllByUser_IdAndOpenInLessThanEqualAndExitGreaterThanEqual(UUID.randomUUID(),
                        Timestamp.valueOf(LocalDateTime.now().minusDays(50)),
                        Timestamp.valueOf(LocalDateTime.now().plusDays(50))
                );
        loggingList(list);
    }

    @Test
    void checkWorkIsExpired(){
        List<Work> list = workRepository.findFinishedWorks();
        loggingList(list);
    }

    @Test
    void checkWorkIsNonExpired(){
        List<Work> list = workRepository.findNonFinishedWorks();
        loggingList(list);
    }

    @Test
    void checkSalaries(){
        List<Salary> allByDate = salaryRepository.findAllByDate("19-04-2022");
        loggingList(allByDate);
    }

    private void loggingList(List<?> list){
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        System.out.println(ANSI_GREEN + "start");
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(e -> {
            System.out.print(i.getAndIncrement() + ". " + e + "\n");
        });
        System.out.println("end" + ANSI_RESET);
    }
}
