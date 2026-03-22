package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PricePerPackDetails")
public class PricePerPackDetails {

    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "LkpPackDetailsId", nullable = false)
    private Integer lkpPackDetailsId;

    @Column(name = "MinAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal minAmount;

    @Column(name = "MaxAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxAmount;

    @Column(name = "CreatedBy", nullable = false, length = 20)
    private String createdBy;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;

    @Column(name = "ModifiedBy", length = 20)
    private String modifiedBy;

    @Column(name = "DistrictID")
    private Integer districtId;

    // ✅ Default Constructor
    public PricePerPackDetails() {
    }

    // ✅ Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLkpPackDetailsId() {
        return lkpPackDetailsId;
    }

    public void setLkpPackDetailsId(Integer lkpPackDetailsId) {
        this.lkpPackDetailsId = lkpPackDetailsId;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }
}
