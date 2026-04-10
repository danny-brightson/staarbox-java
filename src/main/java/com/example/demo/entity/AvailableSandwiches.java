package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "available_sandwiches")
public class AvailableSandwiches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "SandwichName", nullable = false, length = 100)
    private String sandwichName;

    @Column(name = "LkpId", nullable = false)
    private Long lkpId;

    @Column(name = "Rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;

    @Column(name = "createdBy", nullable = false, length = 20)
    private String createdBy;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modifiedBy")
    private Long modifiedBy;

    @Column(name = "modifiedDate")
    private LocalDateTime modifiedDate;

    @Column(name = "DistrictID")
    private Integer districtId;

    @Column(name = "Category", length = 20)
    private String category;

    // ✅ Default Constructor
    public AvailableSandwiches() {
    }

    // ✅ Getters and Setters

    public Long getId() {
        return id;
    }

    public String getSandwichName() {
        return sandwichName;
    }

    public void setSandwichName(String sandwichName) {
        this.sandwichName = sandwichName;
    }

    public Long getLkpId() {
        return lkpId;
    }

    public void setLkpId(Long lkpId) {
        this.lkpId = lkpId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
