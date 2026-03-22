package com.example.demo.dto;


public class CustomerStatusDto {
    private Long customerId;
    private Long statusId;
    private String statusName;

    // Constructor
    public CustomerStatusDto(Long customerId, Long statusId, String statusName) {
        this.customerId = customerId;
        this.statusId = statusId;
        this.statusName = statusName;
    }

    // Getters and setters
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}

