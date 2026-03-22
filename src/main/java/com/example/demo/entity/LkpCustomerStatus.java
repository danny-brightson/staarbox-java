package com.example.demo.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

import org.hibernate.annotations.Where;



@SuppressWarnings("deprecation")
@Entity
@Table(name = "lkpcustomerstatus")
@Where(clause = "StatusId = 1")
public class LkpCustomerStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CreatedTime")
    private LocalDate createdTime;

    @Column(name = "ModifiedTime")
    private LocalDate modifiedTime;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "ModifiedBy")
    private String modifiedBy;

    @Column(name = "Name")
    private String name;

    @Column(name = "StatusId")
    private Integer statusId;

    // Constructors
    public LkpCustomerStatus() {
    }

    public LkpCustomerStatus( LocalDate createdTime, LocalDate modifiedTime,
                             String createdBy, String modifiedBy, String name, Integer statusId) {
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.name = name;
        this.statusId = statusId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDate modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}

