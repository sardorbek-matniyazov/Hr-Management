package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Role;
import com.example.hrmanagement.entity.TourniquetCompany;
import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.payload.*;
import com.example.hrmanagement.repository.CompanyRepository;
import com.example.hrmanagement.repository.RoleRepository;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.UserRepository;
import com.example.hrmanagement.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Slf4j
public record AuthService(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          JavaMailSender mailSender,
                          JwtProvider provider,
                          CompanyRepository companyRepository,
                          TourniquetRepository tourniquetRepository) {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    public Status registerManager(ManagerDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return Status.USERNAME_ALREADY_EXISTS;
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            return Status.EMAIL_ALREADY_EXISTS;
        }
        if (!companyRepository().existsById(dto.getCompanyId())) {
            return Status.COMPANY_NOT_FOUND;
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = dto.toUser();
        if (dto.getRole().equals("MANAGER")) {
            user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.MANAGER)));
        }else if (dto.getRole().equals("HR_MANAGER")) {
            user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.HR_MANAGER)));
        }else
            return Status.INVALID_ROLE;
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setCompany(companyRepository.getById(dto.getCompanyId()));
        String message = "your verification  link is <a href=\"http://localhost:8080/api/auth/verifyEmail?code="
                + user.getVerificationCode()
                + "&email=" + user.getEmail() + "\">click here</a>";
        sendMessage(message, user.getEmail());
        userRepository.save(user);
        return Status.SUCCESS_REGISTER;
    }

    private void setTourniquet(User user){
        tourniquetRepository.save(
                new TourniquetCompany(
                        user.getCompany(),
                        user,
                        Timestamp.valueOf(LocalDateTime.now())
                )
        );
    }

    private void sendMessage(String messageText, String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Please verify your email");
            message.setText(messageText);
            message.setFrom("Anonymous");
            mailSender.send(message);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Exception in sending email", e);
        }
    }

    public Status verifyEmail(String code, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return Status.USER_NOT_FOUND;
        }
        if (user.getVerificationCode() == null)
            return Status.EMAIL_ALREADY_VERIFIED;
        if (user.getVerificationCode().equals(code)) {
            user.setVerificationCode(null);
            user.setEnabled(true);
            user.setToken(provider.generateToken(user.getUsername(), (Set<Role>) user.getAuthorities()));
            userRepository.save(user);
            setTourniquet(user);
            return new Status("Successfully verified", true, user.getToken());
        }
        return Status.FAILED_VERIFICATION;
    }

    public Status registerUser(WorkerDto dto) {

        if (!companyRepository().existsById(dto.getCompanyId())) {
            return Status.COMPANY_NOT_FOUND;
        }
        dto.setUsername(dto.getEmail());
        if (userRepository.existsByUsername(dto.getUsername())) {
            return Status.USERNAME_ALREADY_EXISTS;
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            return Status.EMAIL_ALREADY_EXISTS;
        }

        User user = dto.toUser();

        user.setCompany(companyRepository.getById(dto.getCompanyId()));
        user.setRoles(Collections.singleton(roleRepository.getByRole(RoleName.WORKER)));
        user.setVerificationCode(UUID.randomUUID().toString());
        user.setPassword("password");
        user.setToken(provider.generateToken(user.getUsername(), (Set<Role>) user.getAuthorities()));
        String message = "your verification  link is <a href=\"http://localhost:8080/api/auth/setPassword" +
                " yo'liga post method token orqali o'zingizga password qo'yasiz ! tokenigniz " + user.getToken() ;
        sendMessage(message, user.getEmail());
        userRepository.save(user);
        return Status.SUCCESS_REGISTER;
    }

    public Status login(LoginDto dto) {
        if (!userRepository.existsByUsername(dto.getUsername()))
            return Status.USER_NOT_FOUND;
        User user = userRepository.getByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            return Status.PASSWORD_ERROR;
        AuthenticationManager authenticationManager = authentication -> new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        setTourniquet(user);
        return new Status("Successfully login", true, user.getToken());
    }

    public Status setPasswordUser(@Valid PasswordEditDto dto) {
        if (!dto.getPassword().equals(dto.getPrePassword()))
            return Status.PASSWORD_ERROR;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        user.setPassword(dto.getPassword());
        user.setEnabled(true);
        userRepository.save(user);
        setTourniquet(user);
        return new Status("Successfully login", true, user.getToken());
    }
}
