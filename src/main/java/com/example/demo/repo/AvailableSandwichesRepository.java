package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.SandwichDTO;
import com.example.demo.entity.AvailableSandwiches;

@Repository
	public interface AvailableSandwichesRepository extends JpaRepository<AvailableSandwiches, Long> {

	
	@Query("SELECT new com.example.demo.dto.SandwichDTO(" +
	           "s.lkpId, s.sandwichName, s.rate) " +
	           "FROM AvailableSandwiches s " +
	           "WHERE s.districtId = :districtId " +
	           "ORDER BY s.sandwichName ASC")
	    List<SandwichDTO> findAllByDistrict(Integer districtId);

	    @Query("SELECT new com.example.demo.dto.SandwichDTO(" +
	           "s.lkpId, s.sandwichName, s.rate) " +
	           "FROM AvailableSandwiches s " +
	           "WHERE s.districtId = :districtId " +
	           "AND LOWER(s.category) = LOWER(:category) " +
	           "ORDER BY s.sandwichName ASC")
	    List<SandwichDTO> findByDistrictAndCategory(
	            Integer districtId, String category);
}
