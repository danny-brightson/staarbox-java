package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LkpAvailableDistrict;
import com.example.demo.projection.AvailableDistrictProjection;

@Repository
public interface LkpAvailableDistrictRepo extends JpaRepository<LkpAvailableDistrict, Long> {

	@Query(value = "SELECT Id,District,StateId FROM lkpavailabledistrict WHERE StatusId=1", nativeQuery = true)
	List<AvailableDistrictProjection> getAvailableDistrict();
	
}
