package com.example.demo.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.projection.CustomerPackDistrictProjection;

@Repository
public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Long> {


	@Query(value = "select DistrictId from customerdetails where id = :customerId and StatusId=1", nativeQuery = true)
	int getDistrictId(int customerId);

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE customerdetails SET  PackDetailsId = :packDetailsId,CustomerStatusId =:CustomerStatus WHERE id = :id AND StatusId = 1", nativeQuery = true)
	int updatePackDetails( int packDetailsId, Long id, int CustomerStatus);


	@Modifying
	@Transactional
	@Query("UPDATE CustomerDetails c SET " +
	       "c.newAddressLine1 = :line1, " +
	       "c.newAddressLine2 = :line2, " +
	       "c.newAddressType = :type, " +
	       "c.newDistrictId = :districtId, " +
	       "c.newStateId = :stateId, " +
	       "c.newPinCode = :pinCode, " +
	       "c.newDelivaryTimingId = :timeId, " +
	       "c.isAddressPending = true, " +
	       "c.addressUpdateApplyDate = :applyDate " +
	       "WHERE c.id = :customerId")
	void scheduleAddressChange(
	        Long customerId,
	        String line1,
	        String line2,
	        String type,
	        Long districtId,
	        Long stateId,
	        String pinCode,
	        Long timeId,
	        LocalDate applyDate
	);

	
	@Transactional
	@Modifying
	@Query(value = """
	        UPDATE customerdetails
	           SET IsPaymentSuccess = :isPaymentSuccess,
	               NextRenewalDate   = :nextRenewalDate,
	               OrderId           = :orderId,
	               CustomerStatusId  = :customerStatus,
	               paymentDoneTime   = :paymentDoneTime,
	               IsCustomized      = :isCustomized,
	               IsRenewed         = :isRenewed,
	               RenewedDate       = :renewedDate
	         WHERE Id = :customerId
	           AND StatusId = 1
	        """,
	       nativeQuery = true)
	int updatePaymentStatus(@Param("isPaymentSuccess") boolean isPaymentSuccess,
	                        @Param("nextRenewalDate")  LocalDateTime nextRenewalDate,
	                        @Param("customerId")       Integer customerId,
	                        @Param("orderId")          String orderId,
	                        @Param("customerStatus")   int customerStatus,
	                        @Param("paymentDoneTime")  LocalDateTime paymentDoneTime,
	                        @Param("isCustomized")  int isCustomized,
	                        @Param("isRenewed") boolean isRenewed,
	                        @Param("renewedDate")  LocalDateTime renewedDate);

//	    @Query("SELECT new com.example.demo.dto.PackagingDto(c.id, c.zoneId, c.distanceId, c.districtId, c.deliveryCode, c.packDetailsId) " +
//	    	       "FROM CustomerDetails c " +
//	    	       "WHERE c.nextrenewalDate >= CURRENT_DATE AND c.isPaymentSuccess = true AND c.statusId = 1 " +
//	    	       "ORDER BY c.zoneId ASC, c.distanceId ASC")
//		List<PackagingDto> findAllOrderByZoneDistanceAsc();
//	    @Query(value = """
//	            SELECT 
//	                c.Id,
//	                c.ZoneId,
//	                c.DistanceId,
//	                c.DistrictId,
//	                c.DeliveryCode,
//	                c.PackDetailsId,
//	                c.DelivaryTimingId,
//	                c.Name
//	            FROM 
//	                customerdetails c
//	            WHERE 
//	                c.NextRenewalDate >= CURRENT_DATE
//	                AND c.IsPaymentSuccess = TRUE
//	                AND c.StatusId = 1
//	                And c.DistrictId = : districtId
//	            ORDER BY 
//	                c.DistrictId ASC,
//	                c.DistanceId DESC,
//	                CASE 
//	                    WHEN c.DelivaryTimingId IN (1, 2, 3) THEN 1
//	                    WHEN c.DelivaryTimingId = 4 THEN 2
//	                    ELSE 3
//	                END,
//	                c.DelivaryTimingId ASC
//	            """, nativeQuery = true)
//	        List<Object[]> findAllOrderByZoneDistanceAsc();

	        @Query(value = """
		            SELECT 
		                c.Id,
		                c.ZoneId,
		                c.DistanceId,
		                c.DistrictId,
		                c.DeliveryCode,
		                c.PackDetailsId,
		                c.DelivaryTimingId,
		                c.Name,
		                c.IsPragnent
		            FROM 
		                customerdetails c
		            WHERE 
		                c.NextRenewalDate >= CURRENT_DATE
		                AND c.IsPaymentSuccess = TRUE
		                AND c.StatusId = 1
		                And c.DistrictId = :districtId
		            ORDER BY 
		                c.DistanceId ASC,
		                CASE 
		                    WHEN c.DelivaryTimingId IN (1, 2, 3) THEN 1
		                    WHEN c.DelivaryTimingId = 4 THEN 2
		                    ELSE 3
		                END,
		                c.DelivaryTimingId ASC
		            """, nativeQuery = true)
		List<Object[]> findAllByDistrictIdOrdered(int districtId);
		
		

		@Query(value = "select IsPragnent from customerdetails where id = :customerId and StatusId=1", nativeQuery = true)
		boolean getIsPragnentFlag(int customerId);



		
		Optional<CustomerDetails> findTopByPhoneNumberOrderByCreatedTimeDesc(String phoneNumber);


		
		@Query(value = "SELECT paymentDoneTime  FROM customerdetails WHERE IsRenewed = 0 AND id =:customerId AND IsPaymentSuccess = 1 and StatusId=1;", nativeQuery = true)
		LocalDateTime  checkCustomaizationEnable(int customerId);


		@Modifying
		@Transactional
		@Query(value = "UPDATE customerdetails SET " +
		               "IsCustomized = :customized, " +
		               "LastCustomizedDate = :businessDate, " +
		               "ModefiedBy = :modifiedBy, " +
		               "ModefiedTime = :modifiedTime " +
		               "WHERE Id = :customerId", nativeQuery = true)
		void updateCustomization(
		    @Param("customerId") Long customerId,
		    @Param("customized") Boolean customized,
		    @Param("businessDate") LocalDateTime businessDate,
		    @Param("modifiedBy") String modifiedBy,
		    @Param("modifiedTime") LocalDate modifiedTime
		);

		@Query(value = "select PhoneNumber from customerdetails where PhoneNumber = :phone and StatusId=1 and IsPaymentSuccess =1 LIMIT 1", nativeQuery = true)
		String getPhoneNumber(String phone);

		@Query(
				  value = "SELECT id FROM customerdetails WHERE PhoneNumber = :phoneNumber AND IsPaymentSuccess = 1 AND StatusId=1 AND paymentDoneTime IS NOT NULL",
				  nativeQuery = true
				)
		List<Integer> getCustomerid(String phoneNumber);
		
		
		@Query(
		value = "SELECT id FROM customerdetails WHERE PhoneNumber = :phoneNumber AND IsPaymentSuccess = 0 AND StatusId=1 AND paymentDoneTime IS NULL AND CustomerStatusId in (1,2,3,4)",
				  nativeQuery = true
				)
		List<Integer> getPendingCustomerid(String phoneNumber);


		
		
		@Modifying
		@Transactional
		@Query(value = "UPDATE customerdetails  SET StatusId = 2, ModefiedTime= CURRENT_TIMESTAMP, ModefiedBy = 'user' WHERE Id = :customerId and StatusId=1" , nativeQuery = true)
		int updateCustomerStatus(@Param("customerId") long customerId);


		@Query("""
			    SELECT c FROM CustomerDetails c
			    WHERE c.id = :customerId
			      AND c.statusId = 1
			      AND (
			            (c.isPaymentSuccess = true AND c.paymentDoneTime >= :validDate)
			         OR (c.isRenewed = true AND c.renewedDate >= :validDate)
			      )
			""")
			Optional<CustomerDetails> findActiveCustomer(
			        @Param("customerId") Long customerId,
			        @Param("validDate") LocalDateTime validDate
			);

		@Modifying
		@Transactional
		@Query("UPDATE CustomerDetails c SET c.cancelScheduledDate = :cancelDate, c.isCancelled = false WHERE c.id = :customerId")
		void updateCancelSchedule(@Param("customerId") Long customerId,
		                          @Param("cancelDate") LocalDate cancelDate,
		                          @Param("status") Boolean status);


		@Modifying
		@Transactional
		@Query(value = """
		    UPDATE customerdetails
		    SET 
		        AddressLine1 = :addressLine1,
		        AddressLine2 = :addressLine2,
		        AddressType = :addressType,
		        DistrictId = :districtId,
		        StateId = :stateId,
		        PinCode = :pinCode,
		        DelivaryTimingId = :delivaryTimingId,
		        CustomerLatitude = :customerLatitude,
		        CustomerLongtitude = :customerLongtitude
		    WHERE Id = :customerId
		""", nativeQuery = true)
		int UpdateLocationAddress(
		    @Param("customerId") Long customerId,
		    @Param("addressLine1") String addressLine1,
		    @Param("addressLine2") String addressLine2,
		    @Param("addressType") String addressType,
		    @Param("districtId") Integer districtId,
		    @Param("stateId") Integer stateId,
		    @Param("pinCode") String pinCode,
		    @Param("delivaryTimingId") Integer delivaryTimingId,
		    @Param("customerLatitude") Double customerLatitude,
		    @Param("customerLongtitude") Double customerLongtitude
		);

		@Query("SELECT c.renewedDate FROM CustomerDetails c WHERE c.id = :customerId")
		LocalDateTime findRenewalDetails(@Param("customerId") Long customerId);
		
		
		@Query("""
			       SELECT r.rate 
			       FROM CustomerDetails c 
			       JOIN RateDetails r ON c.packDetailsId = r.planDatailsId
			       AND  r.districtId = c.districtId
			       WHERE c.id = :customerId 
			       """)
			BigDecimal findRateByCustomerId(@Param("customerId") Long customerId );


		@Query("SELECT c.packDetailsId as packDetailsId, " +
			       "c.districtId as districtId " +
			       "FROM CustomerDetails c " +
			       "WHERE c.id = :customerId")
			CustomerPackDistrictProjection 
			findPackAndDistrictByCustomerId(Long customerId);

	@Query(value = "SELECT customized_amount FROM customerdetails WHERE Id = :customerId AND DATE(ModefiedTime) = CURDATE() - INTERVAL 1 DAY", nativeQuery = true)
			Long getYesterdayCustomizedAmount(@Param("customerId") Long customerId);

			@Query(value = "SELECT PackDetailsId FROM customerdetails WHERE Id = :customerId", nativeQuery = true)
			Long getPackageId(@Param("customerId") Long customerId);

			@Query(value = "SELECT rate FROM ratedetails WHERE planDatailsId = :packageId AND districtId = :districtId", nativeQuery = true)
			Long getPackageRate(@Param("packageId") Long packageId, @Param("districtId") Long districtId);

			@Query(value="SELECT districtId FROM customerdetails WHERE id=:customerId", nativeQuery=true)
			Integer findDistrictIdByCustomerId(@Param("customerId") Long customerId);

@Query(value = "SELECT DATE(NextRenewalDate) FROM customerdetails WHERE id = :customerId AND StatusId = 1", nativeQuery = true)
	LocalDate findNextRenewalDateByCustomerId(@Param("customerId") long customerId);

}


}
		
		

		
		

		
	



