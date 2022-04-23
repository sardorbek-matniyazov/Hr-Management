package com.example.hrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TourniquetCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Company company;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Work work;

    private Timestamp openIn;

    private Timestamp exit;

    @CreatedDate
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @LastModifiedDate
    @UpdateTimestamp
    private Timestamp updatedDate;

    public TourniquetCompany(Company company, User user, Timestamp valueOf) {
        this.company = company;
        this.user = user;
        this.openIn = valueOf;
    }

    public TourniquetCompany(Company company, User user, Work work) {
        this.company = company;
        this.user = user;
        this.work = work;
    }

    public TourniquetCompany(Company company, User user) {
        this.company = company;
        this.user = user;
    }
}
