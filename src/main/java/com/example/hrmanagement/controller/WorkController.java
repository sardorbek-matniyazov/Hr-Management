package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.payload.WorkDto;
import com.example.hrmanagement.service.WorkService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/work")
public record WorkController (WorkService service) {

    @PostMapping(value = "/create")
    public HttpEntity<?> create(@Valid @RequestBody WorkDto dto){
        Status create = service.create(dto);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> get(@PathVariable UUID id){
        Status create = service.get(id);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @GetMapping(value = "/done/{id}")
    public HttpEntity<?> done(@PathVariable UUID id){
        Status create = service.done(id);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @GetMapping(value = "/done")
    public HttpEntity<?> doneWork(@RequestParam(value = "id") UUID id){
        Status create = service.doneWork(id);
        return create.success() ? ResponseEntity.ok(create) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(create);
    }

    @GetMapping(value = "/all")
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }


}
