package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LkpState;
import com.example.demo.projection.StateProjection;

@Repository
public interface LkpStateRepo extends JpaRepository<LkpState, Long> {
	
	@Query(value = "SELECT Id,State FROM lkpstate WHERE StatusId=1", nativeQuery = true)
	List<StateProjection> getState();

}
