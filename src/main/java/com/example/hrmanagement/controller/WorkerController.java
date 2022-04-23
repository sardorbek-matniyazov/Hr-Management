package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.service.WorkerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import static com.example.hrmanagement.controller.AuthController.handleValidationExceptions;

@RestController
@RequestMapping(value = "/api/worker")
public record WorkerController(WorkerService service) {
    @GetMapping(value = "/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping(value = "/{id}")
    public HttpEntity<?> get(@PathVariable UUID id,
                             @RequestParam(value = "openIn") Timestamp openIn,
                             @RequestParam(value = "exitIn") Timestamp exitIn){
        Status create = service.get(id, openIn, exitIn);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> exceptionHandler(MethodArgumentNotValidException e){
        return handleValidationExceptions(e);
    }
}
