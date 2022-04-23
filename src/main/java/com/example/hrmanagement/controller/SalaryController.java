package com.example.hrmanagement.controller;

import com.example.hrmanagement.entity.Salary;
import com.example.hrmanagement.payload.SalaryDto;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.service.SalaryService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.hrmanagement.controller.AuthController.handleValidationExceptions;

@RestController(value = "/api/salary")
public record SalaryController(SalaryService service) {
    @PostMapping(value = "/create")
    public HttpEntity<?> create(@Valid @RequestBody SalaryDto dto){
        Status create = service.create(dto);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> exceptionHandler(MethodArgumentNotValidException e){
        return handleValidationExceptions(e);
    }

    @GetMapping(value = "/getByUser/{userId}")
    public HttpEntity<List<Salary>> getById(@PathVariable UUID userId){
        return ResponseEntity.ok(service.getAll(userId));
    }

    @GetMapping(value = "/getByDate/{date}")
    public HttpEntity<List<Salary>> getByDate(@PathVariable String date){
        return ResponseEntity.ok(service.getAllByDate(date));
    }
}
