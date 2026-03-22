package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SpecialCatogoryCustomers;
import com.example.demo.repo.SpecialCatogoryCustomersRepo;

@Service
public class SpecialCatogoryCustomerService {

	@Autowired
	private SpecialCatogoryCustomersRepo specialCatogoryCustomersRepo;

	public List<SpecialCatogoryCustomers> getMenuHierarchy(int genderId) {
		List<SpecialCatogoryCustomers> allMenus = specialCatogoryCustomersRepo.findDistinctByGenderId(genderId);

//		Map<Long, SpecialCatogoryCustomers> menuMap = new HashMap<>();
//
//		// Initialize map with all menus
//		for (SpecialCatogoryCustomers menu : allMenus) {
//			menu.getSubmenus().clear();
//			menuMap.put(menu.getId(), menu);
//		}
//
//		List<SpecialCatogoryCustomers> rootMenus = new ArrayList<>();
//
//		for (SpecialCatogoryCustomers menu : allMenus) {
//			if (menu.getParentmenuId() == null) {
//				rootMenus.add(menuMap.get(menu.getId()));
//			} else {
//				SpecialCatogoryCustomers parent = menuMap.get(menu.getParentmenuId());
//				if (parent != null) {
//					 List<SpecialCatogoryCustomers> submenus = parent.getSubmenus();
//		                if (!submenus.contains(menuMap.get(menu.getId()))) {
//		                    submenus.add(menuMap.get(menu.getId()));
//		                }
//				}
//			}
//		}

		return allMenus;
	}
}
