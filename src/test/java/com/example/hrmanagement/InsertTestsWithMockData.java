package com.example.hrmanagement;

import com.example.hrmanagement.entity.Company;
import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.entity.Work;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.payload.ManagerDto;
import com.example.hrmanagement.payload.SalaryDto;
import com.example.hrmanagement.payload.TourniquetDto;
import com.example.hrmanagement.payload.WorkDto;
import com.example.hrmanagement.repository.CompanyRepository;
import com.example.hrmanagement.repository.RoleRepository;
import com.example.hrmanagement.repository.UserRepository;
import com.example.hrmanagement.repository.WorkRepository;
import com.example.hrmanagement.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class InsertTestsWithMockData {

    /*
    testing step by step
    1. addingCompanies() ->
    2. addingManagers() ->
    3. addingWorkers() ->
    4. no matter

    */
    @Autowired
    AuthService authService;
    @Autowired
    CompanyService companyService;
    @Autowired
    SalaryService salaryService;
    @Autowired
    TourniquetService tourniquetService;
    @Autowired
    WorkerService workerService;
    @Autowired
    WorkService workService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkRepository workRepository;

    @Test
    void addingWork() {
        List<WorkDto> works = Arrays.asList(
                new WorkDto("work1", "you should be carefully", 7L, userRepository.getByUsername("username4").getId()),
                new WorkDto("work2", "you should be carefully", 1L, userRepository.getByUsername("username5").getId()),
                new WorkDto("work3", "you should be carefully", 0L, userRepository.getByUsername("username6").getId()),
                new WorkDto("work4", "you should be carefully", 0L, userRepository.getByUsername("username7").getId()),
                new WorkDto("work5", "you should be carefully", 6L, userRepository.getByUsername("username8").getId()),
                new WorkDto("work6", "you should be carefully", 0L, userRepository.getByUsername("username3").getId()),
                new WorkDto("work10", "you should be carefully", 1L, userRepository.getByUsername("username2").getId()),
                new WorkDto("work9", "you should be carefully", 1L, userRepository.getByUsername("username2").getId()),
                new WorkDto("work8", "you should be carefully", 1L, userRepository.getByUsername("username2").getId())
        );
        works.forEach(workService::create);
    }

    @Test
    void addingManagers() {
        List<ManagerDto> managerDto = Arrays.asList(
                new ManagerDto("Jonathan", "Kimpembe", "username1", 1L, "password1", "HR_MANAGER", "emailN1@gmail.com"),
                new ManagerDto("Floyd", "Mayweather", "username2", 1L, "password2", "MANAGER", "emailN2@gmail.com"),
                new ManagerDto("Fernando", "Torres", "username3", 1L, "password3", "MANAGER", "emailN3@gmail.com"),
                new ManagerDto("Lewandowskiy", "Robert", "username4", 1L, "password4", "MANAGER", "emailN4@gmail.com")

        );
        managerDto.forEach(authService::registerManager);
    }

    @Test
    void updateUsers() {
        List<ManagerDto> managerDto = Arrays.asList(
                new ManagerDto("Jonathan", "Kimpembe", "username1", 1L, "password1", "HR_MANAGER", "emailN1@gmail.com"),
                new ManagerDto("Floyd", "Mayweather", "username2", 1L, "password2", "MANAGER", "emailN2@gmail.com"),
                new ManagerDto("Fernando", "Torres", "username3", 1L, "password3", "MANAGER", "emailN3@gmail.com"),
                new ManagerDto("Lewandowskiy", "Robert", "username4", 1L, "password4", "MANAGER", "emailN4@gmail.com")

        );
        workerService.update(
                userRepository.getByUsername("username3").getId(),
                new ManagerDto("Lewandowskiy", "Robert", "usernagme4", 1L, "password4", "HR_MANAGER", "emailNfgf4@gmail.com")
        );
    }

    @Test
    void addingWorkers() {
        List<User> users = Arrays.asList(
                new User("Joshua", "Kimmich", "username9", companyRepository.getById(1L), "password4", "emailN9@gmail.com", Collections.singleton(roleRepository.getByRole(RoleName.WORKER)), true, true, true, true, "this is the token1"),
                new User("David", "Bekham", "username5", companyRepository.getById(1L), "password5", "emailN5@gmail.com", Collections.singleton(roleRepository.getByRole(RoleName.WORKER)), true, true, true, true, "this is the token2"),
                new User("Mario", "Gomez", "username6", companyRepository.getById(1L), "password6", "emailN6@gmail.com", Collections.singleton(roleRepository.getByRole(RoleName.WORKER)), true, true, true, true, "this is the token3"),
                new User("Manuel", "Neuer", "username7", companyRepository.getById(1L), "password7", "emailN7@gmail.com", Collections.singleton(roleRepository.getByRole(RoleName.WORKER)), true, true, true, true, "this is the token4"),
                new User("Alphonso", "Devies", "username8", companyRepository.getById(1L), "password8", "emailN8@gmail.com", Collections.singleton(roleRepository.getByRole(RoleName.WORKER)), true, true, true, true, "this is the token5")
        );
        try {
            userRepository.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addingCompanies() {
        List<Company> companies = Arrays.asList(
                new Company(1L, "company1"),
                new Company(2L, "company2"),
                new Company(3L, "company3"),
                new Company(4L, "company4")
        );
        companies.forEach(companyService::create);
    }


    @Test
    void finishingWorks() {
        List<Work> works = workRepository.findAll();
        works.forEach(work -> {
            workService.doneWork(work.getId());
        });
    }

    @Test
    void addingSalary() {
        long ONE_DAY = 1000L * 60 * 60 * 24;
        List<SalaryDto> salaryDto = Arrays.asList(
                new SalaryDto(userRepository.getByUsername("username3").getId(), 1000.0, new Date()),
                new SalaryDto(userRepository.getByUsername("username4").getId(), 2000.0, new Date(System.currentTimeMillis() - ONE_DAY * 2)),
                new SalaryDto(userRepository.getByUsername("username3").getId(), 3000.0, new Date(System.currentTimeMillis() - ONE_DAY * 2)),
                new SalaryDto(userRepository.getByUsername("username6").getId(), 4000.0, new Date(System.currentTimeMillis() - ONE_DAY)),
                new SalaryDto(userRepository.getByUsername("username7").getId(), 5000.0, new Date(System.currentTimeMillis() - ONE_DAY * 4)),
                new SalaryDto(userRepository.getByUsername("username2").getId(), 6000.0, new Date(System.currentTimeMillis() - ONE_DAY * 2)),
                new SalaryDto(userRepository.getByUsername("username2").getId(), 7000.0, new Date(System.currentTimeMillis() - ONE_DAY)),
                new SalaryDto(userRepository.getByUsername("username2").getId(), 8000.0, new Date(System.currentTimeMillis() - ONE_DAY * 4))
        );
        salaryDto.forEach(salaryService::create);
    }

    @Test
    void addingTourniquet() {
        List<TourniquetDto> tourniquetDto = Arrays.asList(
                new TourniquetDto(userRepository.getByUsername("username5").getId(), true),
                new TourniquetDto(userRepository.getByUsername("username5").getId(), true),
                new TourniquetDto(userRepository.getByUsername("username3").getId(), false),
                new TourniquetDto(userRepository.getByUsername("username4").getId(), false),
                new TourniquetDto(userRepository.getByUsername("username6").getId(), true),
                new TourniquetDto(userRepository.getByUsername("username6").getId(), false)
        );
        tourniquetDto.forEach(tourniquetService::create);
    }

}
