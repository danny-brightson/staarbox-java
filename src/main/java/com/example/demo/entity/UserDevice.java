package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_device")
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String deviceToken;

    public UserDevice() {}

    public UserDevice(Long userId, String deviceToken) {
        this.userId = userId;
        this.deviceToken = deviceToken;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getDeviceToken() { return deviceToken; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setDeviceToken(String deviceToken) { this.deviceToken = deviceToken; }
}