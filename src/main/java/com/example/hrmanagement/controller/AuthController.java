package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.*;
import com.example.hrmanagement.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public record AuthController (AuthService service) {

    @PostMapping(value = "/createManager")
    public HttpEntity<Status> registerManager(@Valid @RequestBody ManagerDto dto){
        Status register = service.registerManager(dto);
        return register.success() ? ResponseEntity.ok(register) : ResponseEntity.badRequest().body(register);
    }

    @PostMapping(value = "/createWorker")
    public HttpEntity<Status> registerUser(@Valid @RequestBody WorkerDto dto){
        Status register = service.registerUser(dto);
        return register.success() ? ResponseEntity.ok(register) : ResponseEntity.badRequest().body(register);
    }

    @PostMapping(value = "/login")
    public HttpEntity<Status> login(@Valid @RequestBody LoginDto dto, HttpServletResponse response){
        Status register = service.login(dto);
        return register.success() ? ResponseEntity.ok(register) : ResponseEntity.badRequest().body(register);
    }

    @GetMapping(value = "/verifyEmail")
    public HttpEntity<Status> verifyEmail(@RequestParam(value = "code") String code, @RequestParam(value = "email") String email){
        Status register = service.verifyEmail(code, email);
        return register.success() ? ResponseEntity.ok(register) : ResponseEntity.badRequest().body(register);
    }

    @PostMapping(value = "/setPassword")
    public HttpEntity<Status> setPassword(@Valid @RequestBody PasswordEditDto dto){
        Status register = service.setPasswordUser(dto);
        return register.success() ? ResponseEntity.ok(register) : ResponseEntity.badRequest().body(register);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
