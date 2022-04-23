package com.example.hrmanagement;

import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.repository.RoleRepository;
import com.example.hrmanagement.repository.UserRepository;
import com.example.hrmanagement.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@Slf4j
@SpringBootApplication
public class HrManagementApplication {

    @Autowired
    public HrManagementApplication(RoleRepository roleRepository, JwtProvider jwtProvider, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrManagementApplication.class, args);
    }

    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Bean
    public void addDirector(){
        if(!userRepository.existsByEmail("sardorbekmatniyazov03@gmail.com")){
            User save = userRepository.save(
                    new User(
                            "name of director",
                            "surname of director",
                            "director",
                            "password",
                            "sardorbekmatniyazov03@gmail.com",
                            Collections.singleton(roleRepository.getByRole(RoleName.DIRECTOR)),
                            true,
                            true,
                            true,
                            true,
                            jwtProvider.generateToken("director", null)
                    )
            );
            System.out.println("directors token is " + save.getToken());
            log.warn("directors token is " + save.getToken());
        }
    }

}
