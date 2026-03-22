package com.example.demo.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.TodaysDeliveryDetails;


import jakarta.transaction.Transactional;

@Repository
public interface TodaysDeliveryDetailsRepo extends JpaRepository<TodaysDeliveryDetails, Long>{

	@Query(value = "SELECT * FROM todaysdeliverydetails  WHERE StatusId = 1 and DistrictId=:districtId and BoxNumber=:boxnumber", nativeQuery = true)
	Optional<TodaysDeliveryDetails> findByDistrictIdAndBoxnumber(int districtId, long boxnumber);

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE todaysdeliverydetails SET DeliveryboyPhoneNumber = :phoneNumber WHERE Id=:id AND StatusId = 1;", nativeQuery = true)
	int updateDeliveryBoyDetils(Long id, String phoneNumber);

	@Query(value = "SELECT * FROM todaysdeliverydetails  WHERE StatusId = 1 and DeliveryboyPhoneNumber=:phoneNumber", nativeQuery = true)
	List<TodaysDeliveryDetails> findByPhoneNumber(String phoneNumber);
	
	



	
	@Transactional
	@Modifying
	@Query(value = "UPDATE todaysdeliverydetails SET IsDelivered = :isDelivered, ReasonForNotDelivered = :reasonId, ModefiedTime = :modifiedTime, ModefiedBy = :modifiedBy WHERE Id = :id", nativeQuery = true)
	int updateDeliveredStatus(@Param("id") Long id, 
	                          @Param("isDelivered") Boolean isDelivered, 
	                          @Param("reasonId") Integer reasonId,
	                          @Param("modifiedTime") LocalDateTime modifiedTime,
	                          @Param("modifiedBy") String modifiedBy);



	Optional<TodaysDeliveryDetails> findByDeliveryboyPhoneNumberAndBoxNumber(String phoneNumber, long boxnumber);


}
