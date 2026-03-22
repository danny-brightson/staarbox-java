package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LkpDeliveryTimings;
import com.example.demo.projection.DeliveryTimingProjection;

@Repository
public interface LkpDeliveryTimingsRepo extends JpaRepository<LkpDeliveryTimings, Long>{
	
	
	@Query(value = "SELECT Id,DeliveryTiming FROM lkpdeliverytimings WHERE StatusId=1", nativeQuery = true)
	List<DeliveryTimingProjection> getDeliveryTimings();
	

}
