package com.example.hrmanagement.config;

import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.security.MyFilter;
import com.example.hrmanagement.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final MyFilter myFilter;


    @Autowired
    public SecurityConfiguration(MyUserDetailsService myUserDetailsService, MyFilter myFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.myFilter = myFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/createManager", "/api/company/create").hasAuthority(RoleName.DIRECTOR.name())
                .antMatchers("/api/auth/createWorker", "/api/worker/all").hasAnyAuthority(
                        RoleName.HR_MANAGER.name(), RoleName.DIRECTOR.name()
                )
                .antMatchers("/api/auth/verifyEmail").permitAll()
                .antMatchers("/api/auth/setPassword").hasAuthority(
                        RoleName.WORKER.name()
                )
                .antMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("batistabandit@gmail.com");
        mailSender.setPassword("tvqgznilxpgxrkks");
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        return mailSender;
    }
}
