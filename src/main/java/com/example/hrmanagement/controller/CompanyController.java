package com.example.hrmanagement.controller;

import com.example.hrmanagement.entity.Company;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.service.CompanyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.example.hrmanagement.controller.AuthController.handleValidationExceptions;

@RestController
@RequestMapping(value = "/api/company")
public record CompanyController(CompanyService service) {
    @PostMapping(value = "/create")
    public HttpEntity<?> create(@Valid @RequestBody Company dto){
        Status create = service.create(dto);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> exceptionHandler(MethodArgumentNotValidException e){
        return handleValidationExceptions(e);
    }
}
