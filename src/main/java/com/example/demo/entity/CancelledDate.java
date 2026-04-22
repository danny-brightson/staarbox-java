package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CancelledDate")
public class CancelledDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CustomerId", nullable = false)
    private Long customerId;

    @Column(name = "StatusId", nullable = false)
    private Long statusId;

    @Column(name = "CreatedBy", nullable = false)
    private String createdBy;

    @Column(name = "CreatedTime", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "ModefiedBy")
    private String modefiedBy;

    @Column(name = "ModefiedTime")
    private LocalDate modefiedTime;

    @Column(name = "CancelledDate", nullable = false)
    private LocalDate cancelledDate;

    // 🔹 Default Constructor
    public CancelledDate() {
    }

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getModefiedBy() {
        return modefiedBy;
    }

    public void setModefiedBy(String modefiedBy) {
        this.modefiedBy = modefiedBy;
    }

    public LocalDate getModefiedTime() {
        return modefiedTime;
    }

    public void setModefiedTime(LocalDate modefiedTime) {
        this.modefiedTime = modefiedTime;
    }

    public LocalDate getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(LocalDate cancelledDate) {
        this.cancelledDate = cancelledDate;
    }
}

