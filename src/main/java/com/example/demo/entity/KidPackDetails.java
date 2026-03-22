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
@Table(name = "kidpackdetails")
@Where(clause = "StatusId = 1")
public class KidPackDetails {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "WeekdaysId", nullable = false)
	private Byte weekdaysId;

	@Column(name = "IsEggAdded", nullable = false)
	private Integer isEggAdded;

	@Column(name = "EggOrSeed", nullable = false)
	private String eggOrSeed;

	@Column(name = "Fruit1", nullable = false)
	private String fruit1;

	@Column(name = "Fruit1Weight", nullable = false)
	private String fruit1Weight;

	@Column(name = "Fruit2", nullable = false)
	private String fruit2;

	@Column(name = "Fruit2Weight", nullable = false)
	private String fruit2Weight;

	@Column(name = "Fruit3", nullable = false)
	private String fruit3;

	@Column(name = "Fruit3Weight", nullable = false)
	private String fruit3Weight;

	@Column(name = "Nut1", nullable = false)
	private String nut1;

	@Column(name = "Nut1Weight", nullable = false)
	private String nut1Weight;

	@Column(name = "Nut2", nullable = false)
	private String nut2;

	@Column(name = "Nut2Weight", nullable = false)
	private String nut2Weight;

	@Column(name = "Nut3", nullable = false)
	private String nut3;

	@Column(name = "Nut3Weight", nullable = false)
	private String nut3Weight;

	@Column(name = "Nut4")
	private String nut4;

	@Column(name = "Nut4Weight")
	private String nut4Weight;

	@Column(name = "Optional1")
	private String optional1;

	@Column(name = "Optional1Weight")
	private String optional1Weight;

	@Column(name = "Optional2")
	private String optional2;

	@Column(name = "Optional2Weight")
	private String optional2Weight;

	@Column(name = "Optional3")
	private String optional3;

	@Column(name = "Optional3Weight")
	private String optional3Weight;

	@Column(name = "StatusId", nullable = false)
	private Long statusId;

	@Column(name = "CreatedBy", nullable = false)
	private String createdBy;

	@Column(name = "CreatedTime", nullable = false)
	private LocalDateTime createdTime;

	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Column(name = "ModifiedTime")
	private LocalDateTime modifiedTime;

	// ---------- Constructors ----------

	public KidPackDetails() {
	}

	// ---------- Getters and Setters ----------
	

	public Integer getId() {
		return id;
	}

	public KidPackDetails(Integer id, Byte weekdaysId, Integer isEggAdded, String eggOrSeed, String fruit1,
			String fruit1Weight, String fruit2, String fruit2Weight, String fruit3, String fruit3Weight, String nut1,
			String nut1Weight, String nut2, String nut2Weight, String nut3, String nut3Weight, String nut4,
			String nut4Weight, String optional1, String optional1Weight, String optional2, String optional2Weight,
			String optional3, String optional3Weight, Long statusId, String createdBy, LocalDateTime createdTime,
			String modifiedBy, LocalDateTime modifiedTime) {
		super();
		this.id = id;
		this.weekdaysId = weekdaysId;
		this.isEggAdded = isEggAdded;
		this.eggOrSeed = eggOrSeed;
		this.fruit1 = fruit1;
		this.fruit1Weight = fruit1Weight;
		this.fruit2 = fruit2;
		this.fruit2Weight = fruit2Weight;
		this.fruit3 = fruit3;
		this.fruit3Weight = fruit3Weight;
		this.nut1 = nut1;
		this.nut1Weight = nut1Weight;
		this.nut2 = nut2;
		this.nut2Weight = nut2Weight;
		this.nut3 = nut3;
		this.nut3Weight = nut3Weight;
		this.nut4 = nut4;
		this.nut4Weight = nut4Weight;
		this.optional1 = optional1;
		this.optional1Weight = optional1Weight;
		this.optional2 = optional2;
		this.optional2Weight = optional2Weight;
		this.optional3 = optional3;
		this.optional3Weight = optional3Weight;
		this.statusId = statusId;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.modifiedBy = modifiedBy;
		this.modifiedTime = modifiedTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getWeekdaysId() {
		return weekdaysId;
	}

	public void setWeekdaysId(Byte weekdaysId) {
		this.weekdaysId = weekdaysId;
	}

	public Integer getIsEggAdded() {
		return isEggAdded;
	}

	public void setIsEggAdded(Integer isEggAdded) {
		this.isEggAdded = isEggAdded;
	}

	public String getEggOrSeed() {
		return eggOrSeed;
	}

	public void setEggOrSeed(String eggOrSeed) {
		this.eggOrSeed = eggOrSeed;
	}

	public String getFruit1() {
		return fruit1;
	}

	public void setFruit1(String fruit1) {
		this.fruit1 = fruit1;
	}

	public String getFruit1Weight() {
		return fruit1Weight;
	}

	public void setFruit1Weight(String fruit1Weight) {
		this.fruit1Weight = fruit1Weight;
	}

	public String getFruit2() {
		return fruit2;
	}

	public void setFruit2(String fruit2) {
		this.fruit2 = fruit2;
	}

	public String getFruit2Weight() {
		return fruit2Weight;
	}

	public void setFruit2Weight(String fruit2Weight) {
		this.fruit2Weight = fruit2Weight;
	}

	public String getFruit3() {
		return fruit3;
	}

	public void setFruit3(String fruit3) {
		this.fruit3 = fruit3;
	}

	public String getFruit3Weight() {
		return fruit3Weight;
	}

	public void setFruit3Weight(String fruit3Weight) {
		this.fruit3Weight = fruit3Weight;
	}

	public String getNut1() {
		return nut1;
	}

	public void setNut1(String nut1) {
		this.nut1 = nut1;
	}

	public String getNut1Weight() {
		return nut1Weight;
	}

	public void setNut1Weight(String nut1Weight) {
		this.nut1Weight = nut1Weight;
	}

	public String getNut2() {
		return nut2;
	}

	public void setNut2(String nut2) {
		this.nut2 = nut2;
	}

	public String getNut2Weight() {
		return nut2Weight;
	}

	public void setNut2Weight(String nut2Weight) {
		this.nut2Weight = nut2Weight;
	}

	public String getNut3() {
		return nut3;
	}

	public void setNut3(String nut3) {
		this.nut3 = nut3;
	}

	public String getNut3Weight() {
		return nut3Weight;
	}

	public void setNut3Weight(String nut3Weight) {
		this.nut3Weight = nut3Weight;
	}

	public String getNut4() {
		return nut4;
	}

	public void setNut4(String nut4) {
		this.nut4 = nut4;
	}

	public String getNut4Weight() {
		return nut4Weight;
	}

	public void setNut4Weight(String nut4Weight) {
		this.nut4Weight = nut4Weight;
	}

	public String getOptional1() {
		return optional1;
	}

	public void setOptional1(String optional1) {
		this.optional1 = optional1;
	}

	public String getOptional1Weight() {
		return optional1Weight;
	}

	public void setOptional1Weight(String optional1Weight) {
		this.optional1Weight = optional1Weight;
	}

	public String getOptional2() {
		return optional2;
	}

	public void setOptional2(String optional2) {
		this.optional2 = optional2;
	}

	public String getOptional2Weight() {
		return optional2Weight;
	}

	public void setOptional2Weight(String optional2Weight) {
		this.optional2Weight = optional2Weight;
	}

	public String getOptional3() {
		return optional3;
	}

	public void setOptional3(String optional3) {
		this.optional3 = optional3;
	}

	public String getOptional3Weight() {
		return optional3Weight;
	}

	public void setOptional3Weight(String optional3Weight) {
		this.optional3Weight = optional3Weight;
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
}

