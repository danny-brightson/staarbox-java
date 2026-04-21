package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mobile;

    @Column(name = "sms_enabled")
    private boolean smsEnabled = true;

    @Column(name = "sms_count_today")
    private Integer smsCountToday = 0;

    @Column(name = "last_sms_sent")
    private LocalDateTime lastSmsSent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public boolean isSmsEnabled() { return smsEnabled; }
    public void setSmsEnabled(boolean smsEnabled) { this.smsEnabled = smsEnabled; }

    public Integer getSmsCountToday() { return smsCountToday; }
    public void setSmsCountToday(Integer smsCountToday) { this.smsCountToday = smsCountToday; }

    public LocalDateTime getLastSmsSent() { return lastSmsSent; }
    public void setLastSmsSent(LocalDateTime lastSmsSent) { this.lastSmsSent = lastSmsSent; }
}