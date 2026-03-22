package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.Packaging;
import com.example.demo.projection.QrCodeProjection;

import jakarta.transaction.Transactional;

@Repository
public interface PackagingRepo extends JpaRepository<Packaging, Long> {

//	@Modifying
//	@Transactional
//	@Query(value = "TRUNCATE TABLE stagingpackaging", nativeQuery = true)
//	void truncatePackagingTable();
	
	@Query(value = "SELECT QrCode as qrCode, NumberCode as numberCode,CustomerName as customerName FROM stagingpackaging  WHERE StatusId = 1 and DistrictId=:districtId", nativeQuery = true)
	List<QrCodeProjection> fetchAllQrAndCodes(int districtId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM stagingpackaging WHERE districtId = :districtId", nativeQuery = true)
	void deleteByDistrictId(@Param("districtId") int districtId);


	@Query(value = "SELECT * FROM stagingpackaging  WHERE StatusId = 1 and DistrictId=:districtId and BoxNumber=:boxnumber", nativeQuery = true)
	Packaging findByDistrictIdandBoxNumber(int districtId, long boxnumber);
	
	
	@Query(value = """
		    SELECT 
		        sp.PlanCode AS planCode, 
		        COUNT(*) AS planCount,
		        (SELECT COUNT(*) FROM stagingpackaging WHERE StatusId = 1 AND DistrictId = :districtId) AS totalBoxCount
		    FROM stagingpackaging sp
		    WHERE sp.StatusId = 1 AND sp.DistrictId = :districtId
		    GROUP BY sp.PlanCode
		""", nativeQuery = true)
		List<Object[]> getPlanCountsWithTotal(@Param("districtId") int districtId);
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE stagingpackaging SET IsVerified = TRUE WHERE Id=:id AND StatusId = 1;", nativeQuery = true)
		int updateVerification( Long id);



	

}
