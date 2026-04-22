package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AvailablePromoCode;

@Repository
public interface AvailablePromoCodeRepo extends JpaRepository<AvailablePromoCode, Long> {

	
	

	@Query(value = "SELECT * FROM availablepromocode a WHERE a.PromoCode = :promoCode AND a.StatusId = 1", nativeQuery = true)
	Optional<AvailablePromoCode> findValidPromo(@Param("promoCode") String promoCode);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE availablepromocode SET StatusId = 2 WHERE PromoCode = :promoCode AND StatusId = 1", nativeQuery = true)
	int markPromoAsUsed(@Param("promoCode") String promoCode);

	

	@Transactional
	@Modifying
	@Query(value = "UPDATE availablepromocode SET IsValid = FALSE WHERE a.PromoCode = :code", nativeQuery = true)
	void updateStatus(String promoCode);

	
}
