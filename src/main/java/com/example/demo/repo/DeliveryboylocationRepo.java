package com.example.demo.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DeliveryBoyLocation;

import jakarta.transaction.Transactional;

@Repository
public interface DeliveryboylocationRepo extends JpaRepository<DeliveryBoyLocation, Long> {

	 Optional<DeliveryBoyLocation> findTopByDeliveryBoyPhoneNumberOrderByUpdatedAtDesc(String phoneNumber);

	    @Modifying
	    @Transactional
	    @Query("UPDATE DeliveryBoyLocation  SET latitude = :lat, longitude = :lng, updatedAt = :time WHERE deliveryBoyPhoneNumber = :phone")
	    int updateLocation(@Param("phone") String phoneNumber,
	                       @Param("lat") Double latitude,
	                       @Param("lng") Double longitude,
	                       @Param("time") LocalDateTime updatedAt);

	    boolean existsByDeliveryBoyPhoneNumber(String phoneNumber);
	}

