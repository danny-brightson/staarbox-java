package com.example.demo.repo;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PricePerPackDetails;

@Repository
	public interface PricePerPackDetailsRepository extends JpaRepository<PricePerPackDetails, Long> {

	 @Query("SELECT p.minAmount FROM PricePerPackDetails p " +
	           "WHERE p.lkpPackDetailsId = :packId " +
	           "AND p.districtId = :districtId")
	    BigDecimal findMinAmount(
	            @Param("packId") Integer packId,
	            @Param("districtId") Integer districtId);
	
}
