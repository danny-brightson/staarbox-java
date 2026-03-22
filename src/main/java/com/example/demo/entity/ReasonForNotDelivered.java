package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lkpreasonfornotdeliverd")
public class ReasonForNotDelivered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "StatusId")
    private Long statusId;

    @Column(name = "Reason", length = 555)
    private String reason;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedTime")
    private LocalDateTime createdTime;

    @Column(name = "ModefiedBy")
    private String modefiedBy;

    @Column(name = "ModefiedTime")
    private LocalDate modefiedTime;

    // Constructors
    public ReasonForNotDelivered() {
    }

    public ReasonForNotDelivered(Long id, Long statusId, String reason, String createdBy, LocalDateTime createdTime,
                                 String modefiedBy, LocalDate modefiedTime) {
        this.id = id;
        this.statusId = statusId;
        this.reason = reason;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modefiedBy = modefiedBy;
        this.modefiedTime = modefiedTime;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
}

