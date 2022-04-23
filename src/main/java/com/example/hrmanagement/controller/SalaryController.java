package com.example.hrmanagement.controller;

import com.example.hrmanagement.service.SalaryService;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/salary")
public record SalaryController(SalaryService service) {
}
