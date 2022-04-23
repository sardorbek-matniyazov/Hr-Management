package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.payload.WorkDto;
import com.example.hrmanagement.service.WorkService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static com.example.hrmanagement.controller.AuthController.handleValidationExceptions;

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

    @GetMapping(value = "/allFinished")
    public HttpEntity<?> getFinished(){
        return ResponseEntity.ok(service.getFinished());
    }

    @GetMapping(value = "/allNonFinished")
    public HttpEntity<?> getNonFinished(){
        return ResponseEntity.ok(service.getNonFinished());
    }

    @PutMapping(value = "/update/{id}")
    public HttpEntity<?> update(@PathVariable UUID id, @Valid @RequestBody WorkDto dto){
        Status update = service.update(id, dto);
        return update.success() ? ResponseEntity.ok(update) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(update);
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id){
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
