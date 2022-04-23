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

    @PutMapping(value = "/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @Valid @RequestBody Company dto){
        Status update = service.update(id, dto);
        return update.success() ? ResponseEntity.ok(update) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(update);
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id){
        Status delete = service.delete(id);
        return delete.success() ? ResponseEntity.ok(delete) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> exceptionHandler(MethodArgumentNotValidException e){
        return handleValidationExceptions(e);
    }
}
