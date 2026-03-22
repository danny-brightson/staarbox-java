package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.LkpAvailablePincodes;


@Repository
public interface LkpAvailablePincodesRepo extends JpaRepository<LkpAvailablePincodes, Long> {

	
	
	@Query(value = "SELECT District  FROM availablepincodes WHERE PinCode = :pincode And StatusId=1", nativeQuery = true)
	String checkPincode(String pincode);

	Optional<LkpAvailablePincodes> findByPinCode(String pinCode);

	
	
}