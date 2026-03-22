package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.SpecialCatogoryCustomers;
import com.example.demo.service.SpecialCatogoryCustomerService;

@RestController
@RequestMapping("/api")
public class specialCatogoryCustomersRest {

	@Autowired
	private SpecialCatogoryCustomerService specialCatogoryCustomerService;

	@GetMapping("/getMenu")
	public List<SpecialCatogoryCustomers> getMenusByGender(@RequestParam int genderId) {
		return specialCatogoryCustomerService.getMenuHierarchy(genderId);
	}

}
