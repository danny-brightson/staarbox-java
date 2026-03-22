package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lkpfruitandnuts")
public class LkpFruitAndNuts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "FruitAndNuts")
	private String fruitAndNuts;

	@Column(name = "Code")
	private String code;

	@Column(name = "Protein")
	private BigDecimal protein;

	@Column(name = "Fat")
	private BigDecimal fat;

	@Column(name = "CarboHydreate")
	private BigDecimal carboHydreate;

	@Column(name = "Sugar")
	private BigDecimal sugar;

	@Column(name = "Fiber")
	private BigDecimal fiber;

	@Column(name = "StatusId")
	private Integer statusId;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedTime")
	private LocalDateTime createdTime;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedTime")
	private LocalDateTime modifiedTime;

	@Column(name = "Category")
	private String category;

	public LkpFruitAndNuts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LkpFruitAndNuts(Integer id, String fruitAndNuts, String code, BigDecimal protein, BigDecimal fat,
			BigDecimal carboHydreate, BigDecimal sugar, BigDecimal fiber, Integer statusId, String createdBy,
			LocalDateTime createdTime, String modifiedBy, LocalDateTime modifiedTime, String category) {
		super();
		this.id = id;
		this.fruitAndNuts = fruitAndNuts;
		this.code = code;
		this.protein = protein;
		this.fat = fat;
		this.carboHydreate = carboHydreate;
		this.sugar = sugar;
		this.fiber = fiber;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modifiedBy = modifiedBy;
		this.modifiedTime = modifiedTime;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFruitAndNuts() {
		return fruitAndNuts;
	}

	public void setFruitAndNuts(String fruitAndNuts) {
		this.fruitAndNuts = fruitAndNuts;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getProtein() {
		return protein;
	}

	public void setProtein(BigDecimal protein) {
		this.protein = protein;
	}

	public BigDecimal getFat() {
		return fat;
	}

	public void setFat(BigDecimal fat) {
		this.fat = fat;
	}

	public BigDecimal getCarboHydreate() {
		return carboHydreate;
	}

	public void setCarboHydreate(BigDecimal carboHydreate) {
		this.carboHydreate = carboHydreate;
	}

	public BigDecimal getSugar() {
		return sugar;
	}

	public void setSugar(BigDecimal sugar) {
		this.sugar = sugar;
	}

	public BigDecimal getFiber() {
		return fiber;
	}

	public void setFiber(BigDecimal fiber) {
		this.fiber = fiber;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
