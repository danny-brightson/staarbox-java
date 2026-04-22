package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PackerItemStatus;

@Repository
public interface PackerItemStatusRepo extends JpaRepository<PackerItemStatus, Long> {

	Optional<PackerItemStatus> findByBoxNumberAndDistrictIdAndProductId(Long boxNumber, Integer districtId,
			long productId);

	@Modifying
	@Transactional
	@Query("DELETE FROM PackerItemStatus p WHERE p.customerId = :customerId")
	void deleteByCustomerId(@Param("customerId") Long customerId);

}
