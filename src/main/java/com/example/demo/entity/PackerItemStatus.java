package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "packeritemstatus")
public class PackerItemStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "BoxNumber")
	private Long boxNumber;
    
    @Column(name = "DistrictId")
    private Integer districtId;
    
    @Column(name = "CustomerId")
    private Long customerId;
    
    @Column(name = "PackerId")
    private Long packerId;
    
    @Column(name = "ProductId")
    private Long productId;

    @Column(name = "Packed")
    private Boolean packed;

    @Column(name = "PackedTime")
    private LocalDateTime packedTime;



	public PackerItemStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	


	public PackerItemStatus(Long id, Long boxNumber, Integer districtId, Long customerId, Long packerId,
			Long productId, Boolean packed, LocalDateTime packedTime) {
		super();
		this.id = id;
		this.boxNumber = boxNumber;
		this.districtId = districtId;
		this.customerId = customerId;
		this.packerId = packerId;
		this.productId = productId;
		this.packed = packed;
		this.packedTime = packedTime;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Long boxNumber) {
		this.boxNumber = boxNumber;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Long getPackerId() {
		return packerId;
	}

	public void setPackerId(Long packerId) {
		this.packerId = packerId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Boolean getPacked() {
		return packed;
	}

	public void setPacked(Boolean packed) {
		this.packed = packed;
	}

	public LocalDateTime getPackedTime() {
		return packedTime;
	}

	public void setPackedTime(LocalDateTime packedTime) {
		this.packedTime = packedTime;
	}






	public Long getCustomerId() {
		return customerId;
	}






	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

    // getters & setters
    
}

