package com.example.demo.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LkpGender;
import com.example.demo.projection.GenderProjection;

@Repository
public interface LkpGenderRepo extends JpaRepository<LkpGender, Long> {

	@Query(value = "SELECT Id,GenderName FROM lkpgender WHERE StatusId=1", nativeQuery = true)
	List<GenderProjection> getGender();
	
}
