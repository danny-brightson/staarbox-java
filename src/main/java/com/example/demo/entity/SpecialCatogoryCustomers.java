package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "SpecialCatogoryCustomers")
@Where(clause = "StatusId = 1")
public class SpecialCatogoryCustomers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "[MenuName]")
	private String menuname;

	@Column(name = "ParentmenuId")
	private Long parentmenuId;

	@Column(name = "GenderId")
	private Long genderId;
	
	 @OneToMany
	 @JoinColumn(name = "parentmenuId", referencedColumnName = "id") 
	 @JsonManagedReference
	private List<SpecialCatogoryCustomers> submenus = new ArrayList<>();
	 
	 @ManyToOne
	 @JoinColumn(name = "parentmenuId", insertable = false, updatable = false)
	 @JsonBackReference
	 private SpecialCatogoryCustomers parent;

	public SpecialCatogoryCustomers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SpecialCatogoryCustomers(Long id, String menuname, Long parentmenuId, Long genderId) {
		super();
		this.id = id;
		this.menuname = menuname;
		this.parentmenuId = parentmenuId;
		this.genderId = genderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public Long getParentmenuId() {
		return parentmenuId;
	}

	public void setParentmenuId(Long parentmenuId) {
		this.parentmenuId = parentmenuId;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public List<SpecialCatogoryCustomers> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<SpecialCatogoryCustomers> submenus) {
		this.submenus = submenus;
	}
	
	

}
