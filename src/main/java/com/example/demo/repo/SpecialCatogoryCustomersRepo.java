package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SpecialCatogoryCustomers;
@Repository
public interface SpecialCatogoryCustomersRepo   extends JpaRepository<SpecialCatogoryCustomers, Long> {

	 List<SpecialCatogoryCustomers> findDistinctByGenderId(int genderId);
}
