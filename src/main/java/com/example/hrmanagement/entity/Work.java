package com.example.hrmanagement.entity;

import com.example.hrmanagement.entity.enums.StatusName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Work {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date expiredDate;

    @ManyToOne(optional = false)
    private User worker;

    @CreatedBy
    private UUID createdBy;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    private Timestamp finishedDate;

    @Enumerated(EnumType.STRING)
    private StatusName statusName;

    public Work(String name,
                String description,
                Date expireDate,
                User byId,
                StatusName aNew) {
        this.name = name;
        this.description = description;
        this.expiredDate = expireDate;
        this.worker = byId;
        this.statusName = aNew;
    }
}