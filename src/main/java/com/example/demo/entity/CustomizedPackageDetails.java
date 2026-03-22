package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "customizedpackagedetails")
@Where(clause = "StatusId = 1")
public class CustomizedPackageDetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Id")
	    private Long id;

	    @Column(name = "CustomerId", nullable = false)
	    private Long customerId;

	    @Column(name = "CustomizedDate", nullable = false)
	    private LocalDateTime customizedDate;

	    @Column(name = "WeekdaysId", nullable = false)
	    private Integer weekdaysId;

	    @Column(name = "IsEggAdded")
	    private Boolean isEggAdded;

	    @Column(name = "EggOrSeed")
	    private Integer eggOrSeed;

	    @Column(name = "EggOrSeedWeight")
	    private String eggOrSeedWeight;

	    // Fruit fields
	    @Column(name = "Fruit1Id")
	    private Integer fruit1Id;
	    @Column(name = "Fruit1Weight")
	    private String fruit1Weight;

	    @Column(name = "Fruit2Id")
	    private Integer fruit2Id;
	    @Column(name = "Fruit2Weight")
	    private String fruit2Weight;

	    @Column(name = "Fruit3Id")
	    private Integer fruit3Id;
	    @Column(name = "Fruit3Weight")
	    private String fruit3Weight;

	    @Column(name = "Fruit4Id")
	    private Integer fruit4Id;
	    @Column(name = "Fruit4Weight")
	    private String fruit4Weight;

	    @Column(name = "Fruit5Id")
	    private Integer fruit5Id;
	    @Column(name = "Fruit5Weight")
	    private String fruit5Weight;

	    // Nut fields
	    @Column(name = "Nut1Id")
	    private Integer nut1Id;
	    @Column(name = "Nut1Weight")
	    private String nut1Weight;

	    @Column(name = "Nut2Id")
	    private Integer nut2Id;
	    @Column(name = "Nut2Weight")
	    private String nut2Weight;

	    @Column(name = "Nut3Id")
	    private Integer nut3Id;
	    @Column(name = "Nut3Weight")
	    private String nut3Weight;

	    @Column(name = "Nut4Id")
	    private Integer nut4Id;
	    @Column(name = "Nut4Weight")
	    private String nut4Weight;

	    @Column(name = "Nut5Id")
	    private Integer nut5Id;
	    @Column(name = "Nut5Weight")
	    private String nut5Weight;

	    // Optional fields with isFruit flag
	    @Column(name = "Optional1Id")
	    private Integer optional1Id;
	    @Column(name = "Optional1Weight")
	    private String optional1Weight;
	    @Column(name = "Optional1IsFruit")
	    private Boolean optional1IsFruit;

	    @Column(name = "Optional2Id")
	    private Integer optional2Id;
	    @Column(name = "Optional2Weight")
	    private String optional2Weight;
	    @Column(name = "Optional2IsFruit")
	    private Boolean optional2IsFruit;

	    @Column(name = "Optional3Id")
	    private Integer optional3Id;
	    @Column(name = "Optional3Weight")
	    private String optional3Weight;
	    @Column(name = "Optional3IsFruit")
	    private Boolean optional3IsFruit;

	    @Column(name = "Optional4Id")
	    private Integer optional4Id;
	    @Column(name = "Optional4Weight")
	    private String optional4Weight;
	    @Column(name = "Optional4IsFruit")
	    private Boolean optional4IsFruit;

	    @Column(name = "Optional5Id")
	    private Integer optional5Id;
	    @Column(name = "Optional5Weight")
	    private String optional5Weight;
	    @Column(name = "Optional5IsFruit")
	    private Boolean optional5IsFruit;

	    @Column(name = "Optional6Id")
	    private Integer optional6Id;
	    @Column(name = "Optional6Weight")
	    private String optional6Weight;
	    @Column(name = "Optional6IsFruit")
	    private Boolean optional6IsFruit;

	    // Status and audit
	    @Column(name = "StatusId", nullable = false)
	    private Integer statusId;

	    @Column(name = "CreatedBy")
	    private String createdBy;

	    @Column(name = "CreatedTime")
	    private LocalDateTime createdTime;

	    @Column(name = "ModifiedBy")
	    private String modifiedBy;

	    @Column(name = "ModifiedTime")
	    private LocalDateTime modifiedTime;
	    
	    @Column(name = "SandwichId")
	    private Integer sandwichId;

	    @Column(name = "Fruit6Id")
	    private Integer fruit6Id;
	    @Column(name = "Fruit6Weight")
	    private String fruit6Weight;
	    
	    // Getters, setters, and constructors as needed



	public CustomizedPackageDetails() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CustomizedPackageDetails(Long id, Long customerId, LocalDateTime customizedDate, Integer weekdaysId,
				Boolean isEggAdded, Integer eggOrSeed, String eggOrSeedWeight, Integer fruit1Id, String fruit1Weight,
				Integer fruit2Id, String fruit2Weight, Integer fruit3Id, String fruit3Weight, Integer fruit4Id,
				String fruit4Weight, Integer fruit5Id, String fruit5Weight, Integer nut1Id, String nut1Weight,
				Integer nut2Id, String nut2Weight, Integer nut3Id, String nut3Weight, Integer nut4Id, String nut4Weight,
				Integer nut5Id, String nut5Weight, Integer optional1Id, String optional1Weight,
				Boolean optional1IsFruit, Integer optional2Id, String optional2Weight, Boolean optional2IsFruit,
				Integer optional3Id, String optional3Weight, Boolean optional3IsFruit, Integer optional4Id,
				String optional4Weight, Boolean optional4IsFruit, Integer optional5Id, String optional5Weight,
				Boolean optional5IsFruit, Integer optional6Id, String optional6Weight, Boolean optional6IsFruit,
				Integer statusId, String createdBy, LocalDateTime createdTime, String modifiedBy,
				LocalDateTime modifiedTime, Integer sandwichId, Integer fruit6Id, String fruit6Weight) {
			super();
			this.id = id;
			this.customerId = customerId;
			this.customizedDate = customizedDate;
			this.weekdaysId = weekdaysId;
			this.isEggAdded = isEggAdded;
			this.eggOrSeed = eggOrSeed;
			this.eggOrSeedWeight = eggOrSeedWeight;
			this.fruit1Id = fruit1Id;
			this.fruit1Weight = fruit1Weight;
			this.fruit2Id = fruit2Id;
			this.fruit2Weight = fruit2Weight;
			this.fruit3Id = fruit3Id;
			this.fruit3Weight = fruit3Weight;
			this.fruit4Id = fruit4Id;
			this.fruit4Weight = fruit4Weight;
			this.fruit5Id = fruit5Id;
			this.fruit5Weight = fruit5Weight;
			this.nut1Id = nut1Id;
			this.nut1Weight = nut1Weight;
			this.nut2Id = nut2Id;
			this.nut2Weight = nut2Weight;
			this.nut3Id = nut3Id;
			this.nut3Weight = nut3Weight;
			this.nut4Id = nut4Id;
			this.nut4Weight = nut4Weight;
			this.nut5Id = nut5Id;
			this.nut5Weight = nut5Weight;
			this.optional1Id = optional1Id;
			this.optional1Weight = optional1Weight;
			this.optional1IsFruit = optional1IsFruit;
			this.optional2Id = optional2Id;
			this.optional2Weight = optional2Weight;
			this.optional2IsFruit = optional2IsFruit;
			this.optional3Id = optional3Id;
			this.optional3Weight = optional3Weight;
			this.optional3IsFruit = optional3IsFruit;
			this.optional4Id = optional4Id;
			this.optional4Weight = optional4Weight;
			this.optional4IsFruit = optional4IsFruit;
			this.optional5Id = optional5Id;
			this.optional5Weight = optional5Weight;
			this.optional5IsFruit = optional5IsFruit;
			this.optional6Id = optional6Id;
			this.optional6Weight = optional6Weight;
			this.optional6IsFruit = optional6IsFruit;
			this.statusId = statusId;
			this.createdBy = createdBy;
			this.createdTime = createdTime;
			this.modifiedBy = modifiedBy;
			this.modifiedTime = modifiedTime;
			this.sandwichId = sandwichId;
			this.fruit6Id = fruit6Id;
			this.fruit6Weight = fruit6Weight;
		}


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

	public LocalDateTime getCustomizedDate() {
		return customizedDate;
	}

	public void setCustomizedDate(LocalDateTime customizedDate) {
		this.customizedDate = customizedDate;
	}

	public Integer getWeekdaysId() {
		return weekdaysId;
	}

	public void setWeekdaysId(Integer weekdaysId) {
		this.weekdaysId = weekdaysId;
	}

	public Boolean getIsEggAdded() {
		return isEggAdded;
	}

	public void setIsEggAdded(Boolean isEggAdded) {
		this.isEggAdded = isEggAdded;
	}

	public Integer getEggOrSeed() {
		return eggOrSeed;
	}

	public void setEggOrSeed(Integer eggOrSeed) {
		this.eggOrSeed = eggOrSeed;
	}

	public Integer getFruit1Id() {
		return fruit1Id;
	}

	public void setFruit1Id(Integer fruit1Id) {
		this.fruit1Id = fruit1Id;
	}

	public String getFruit1Weight() {
		return fruit1Weight;
	}

	public void setFruit1Weight(String fruit1Weight) {
		this.fruit1Weight = fruit1Weight;
	}

	public Integer getFruit2Id() {
		return fruit2Id;
	}

	public void setFruit2Id(Integer fruit2Id) {
		this.fruit2Id = fruit2Id;
	}

	public String getFruit2Weight() {
		return fruit2Weight;
	}

	public void setFruit2Weight(String fruit2Weight) {
		this.fruit2Weight = fruit2Weight;
	}

	public Integer getFruit3Id() {
		return fruit3Id;
	}

	public void setFruit3Id(Integer fruit3Id) {
		this.fruit3Id = fruit3Id;
	}

	public String getFruit3Weight() {
		return fruit3Weight;
	}

	public void setFruit3Weight(String fruit3Weight) {
		this.fruit3Weight = fruit3Weight;
	}

	public Integer getFruit4Id() {
		return fruit4Id;
	}

	public void setFruit4Id(Integer fruit4Id) {
		this.fruit4Id = fruit4Id;
	}

	public String getFruit4Weight() {
		return fruit4Weight;
	}

	public void setFruit4Weight(String fruit4Weight) {
		this.fruit4Weight = fruit4Weight;
	}

	public Integer getFruit5Id() {
		return fruit5Id;
	}

	public void setFruit5Id(Integer fruit5Id) {
		this.fruit5Id = fruit5Id;
	}

	public String getFruit5Weight() {
		return fruit5Weight;
	}

	public void setFruit5Weight(String fruit5Weight) {
		this.fruit5Weight = fruit5Weight;
	}

	public Integer getNut1Id() {
		return nut1Id;
	}

	public void setNut1Id(Integer nut1Id) {
		this.nut1Id = nut1Id;
	}

	public String getNut1Weight() {
		return nut1Weight;
	}

	public void setNut1Weight(String nut1Weight) {
		this.nut1Weight = nut1Weight;
	}

	public Integer getNut2Id() {
		return nut2Id;
	}

	public void setNut2Id(Integer nut2Id) {
		this.nut2Id = nut2Id;
	}

	public String getNut2Weight() {
		return nut2Weight;
	}

	public void setNut2Weight(String nut2Weight) {
		this.nut2Weight = nut2Weight;
	}

	public Integer getNut3Id() {
		return nut3Id;
	}

	public void setNut3Id(Integer nut3Id) {
		this.nut3Id = nut3Id;
	}

	public String getNut3Weight() {
		return nut3Weight;
	}

	public void setNut3Weight(String nut3Weight) {
		this.nut3Weight = nut3Weight;
	}

	public Integer getNut4Id() {
		return nut4Id;
	}

	public void setNut4Id(Integer nut4Id) {
		this.nut4Id = nut4Id;
	}

	public String getNut4Weight() {
		return nut4Weight;
	}

	public void setNut4Weight(String nut4Weight) {
		this.nut4Weight = nut4Weight;
	}

	public Integer getNut5Id() {
		return nut5Id;
	}

	public void setNut5Id(Integer nut5Id) {
		this.nut5Id = nut5Id;
	}

	public String getNut5Weight() {
		return nut5Weight;
	}

	public void setNut5Weight(String nut5Weight) {
		this.nut5Weight = nut5Weight;
	}

	public Integer getOptional1Id() {
		return optional1Id;
	}

	public void setOptional1Id(Integer optional1Id) {
		this.optional1Id = optional1Id;
	}

	public String getOptional1Weight() {
		return optional1Weight;
	}

	public void setOptional1Weight(String optional1Weight) {
		this.optional1Weight = optional1Weight;
	}

	public Integer getOptional2Id() {
		return optional2Id;
	}

	public void setOptional2Id(Integer optional2Id) {
		this.optional2Id = optional2Id;
	}

	public String getOptional2Weight() {
		return optional2Weight;
	}

	public void setOptional2Weight(String optional2Weight) {
		this.optional2Weight = optional2Weight;
	}

	public Integer getOptional3Id() {
		return optional3Id;
	}

	public void setOptional3Id(Integer optional3Id) {
		this.optional3Id = optional3Id;
	}

	public String getOptional3Weight() {
		return optional3Weight;
	}

	public void setOptional3Weight(String optional3Weight) {
		this.optional3Weight = optional3Weight;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}



	public String getEggOrSeedWeight() {
		return eggOrSeedWeight;
	}



	public void setEggOrSeedWeight(String eggOrSeedWeight) {
		this.eggOrSeedWeight = eggOrSeedWeight;
	}



	public Boolean getOptional1IsFruit() {
		return optional1IsFruit;
	}



	public void setOptional1IsFruit(Boolean optional1IsFruit) {
		this.optional1IsFruit = optional1IsFruit;
	}



	public Boolean getOptional2IsFruit() {
		return optional2IsFruit;
	}



	public void setOptional2IsFruit(Boolean optional2IsFruit) {
		this.optional2IsFruit = optional2IsFruit;
	}



	public Boolean getOptional3IsFruit() {
		return optional3IsFruit;
	}



	public void setOptional3IsFruit(Boolean optional3IsFruit) {
		this.optional3IsFruit = optional3IsFruit;
	}



	public Integer getOptional4Id() {
		return optional4Id;
	}



	public void setOptional4Id(Integer optional4Id) {
		this.optional4Id = optional4Id;
	}



	public String getOptional4Weight() {
		return optional4Weight;
	}



	public void setOptional4Weight(String optional4Weight) {
		this.optional4Weight = optional4Weight;
	}



	public Boolean getOptional4IsFruit() {
		return optional4IsFruit;
	}



	public void setOptional4IsFruit(Boolean optional4IsFruit) {
		this.optional4IsFruit = optional4IsFruit;
	}



	public Integer getOptional5Id() {
		return optional5Id;
	}



	public void setOptional5Id(Integer optional5Id) {
		this.optional5Id = optional5Id;
	}



	public String getOptional5Weight() {
		return optional5Weight;
	}



	public void setOptional5Weight(String optional5Weight) {
		this.optional5Weight = optional5Weight;
	}



	public Boolean getOptional5IsFruit() {
		return optional5IsFruit;
	}



	public void setOptional5IsFruit(Boolean optional5IsFruit) {
		this.optional5IsFruit = optional5IsFruit;
	}



	public Integer getOptional6Id() {
		return optional6Id;
	}



	public void setOptional6Id(Integer optional6Id) {
		this.optional6Id = optional6Id;
	}



	public String getOptional6Weight() {
		return optional6Weight;
	}



	public void setOptional6Weight(String optional6Weight) {
		this.optional6Weight = optional6Weight;
	}



	public Boolean getOptional6IsFruit() {
		return optional6IsFruit;
	}



	public void setOptional6IsFruit(Boolean optional6IsFruit) {
		this.optional6IsFruit = optional6IsFruit;
	}






	public Integer getSandwichId() {
		return sandwichId;
	}






	public void setSandwichId(Integer sandwichId) {
		this.sandwichId = sandwichId;
	}


	public Integer getFruit6Id() {
		return fruit6Id;
	}


	public void setFruit6Id(Integer fruit6Id) {
		this.fruit6Id = fruit6Id;
	}


	public String getFruit6Weight() {
		return fruit6Weight;
	}


	public void setFruit6Weight(String fruit6Weight) {
		this.fruit6Weight = fruit6Weight;
	}
    
    // Getters and Setters...
	
	
}

