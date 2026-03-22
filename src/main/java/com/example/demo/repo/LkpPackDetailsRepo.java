package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LkpPackDetails;
import com.example.demo.projection.PackDetailsProjection;

@Repository
public interface LkpPackDetailsRepo  extends JpaRepository<LkpPackDetails, Long>{
	
	@Query(value = """
		    SELECT 
		        p.Id, 
		        p.PackageName, 
		        p.DisplayOrder,
		        r.Rate
		    FROM lkppackdetails p
		    JOIN ratedetails r 
		        ON p.Id = r.PlanDatailsId
		    WHERE 
		        r.DistrictId = :districtId 
		        AND r.StatusId = 1 
		        AND p.StatusId = 1
		    ORDER BY 
		        p.DisplayOrder, 
		        r.PlanDatailsId
		    """, nativeQuery = true)
		List<PackDetailsProjection> getPackDetails(int districtId);
;

}
