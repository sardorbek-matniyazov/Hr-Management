package com.example.hrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Company name is required")
    @Column(nullable = false)
    private String name;

    public Company(String name) {
        this.name = name;
    }

    public Company toCompany() {
        return new Company(this.name);
    }
}
