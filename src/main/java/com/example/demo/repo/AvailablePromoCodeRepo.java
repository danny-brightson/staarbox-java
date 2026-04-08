package com.example.demo.repo;

import java.time.LocalDateTime;
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

	
	

	// @Query(value = "SELECT * FROM availablepromocode a WHERE a.PromoCode = :promoCode AND a.StatusId = 1", nativeQuery = true)
	// Optional<AvailablePromoCode> findValidPromo(@Param("promoCode") String promoCode, @Param("userId") Long userId);
	@Query(value = "SELECT * FROM availablepromocode WHERE PromoCode = :promoCode AND StatusId = 1", nativeQuery = true)
	Optional<AvailablePromoCode> findValidPromo(@Param("promoCode") String promoCode);
	
	// @Query(value = "SELECT PromoCodeUsed FROM customerdetails WHERE Id = :customerId", nativeQuery = true)
	// String getPromoCodeUsed(@Param("customerId") Long customerId);

	// @Query("SELECT c.districtId FROM CustomerDetails c WHERE c.id = :customerId")
	// Integer findDistrictIdByCustomerId(@Param("customerId") Long customerId);

	// @Query("SELECT c.packDetailsId FROM CustomerDetails c WHERE c.id = :customerId")
	// Long getPackageId(@Param("customerId") Long customerId);
	// @Transactional
	// @Modifying
	// @Query(value = "UPDATE availablepromocode SET IsValid = FALSE WHERE a.PromoCode = :code", nativeQuery = true)
	// void updateStatus(String promoCode);


	@Modifying
	@Transactional
	@Query(value = """
		UPDATE customerdetails
		SET 
			NextRenewalDate = :nextRenewDate,
			PromoCodeUsed = :promoCode,
			IsPaymentSuccess = 1,
			CustomerStatusId = 5,
			paymentDoneTime = CURRENT_TIMESTAMP
		WHERE Id = :customerId
	""", nativeQuery = true)
	int applyFreeTrial(
		@Param("customerId") Long customerId,
		@Param("nextRenewDate") LocalDateTime nextRenewDate,
		@Param("promoCode") String promoCode
	);
}
