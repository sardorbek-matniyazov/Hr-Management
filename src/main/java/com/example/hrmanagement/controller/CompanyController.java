package com.example.hrmanagement.controller;

import com.example.hrmanagement.entity.Company;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.service.CompanyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/company")
public record CompanyController(CompanyService service) {
    @PostMapping(value = "/create")
    public HttpEntity<?> create(@Valid @RequestBody Company dto){
        Status create = service.create(dto);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }
}
