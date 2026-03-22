package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PackageType;

@Repository
public interface PackageTypeRepo extends JpaRepository<PackageType, Long>  {

	@Query(value = "SELECT Id FROM package_types WHERE IsPregnant = :isPregnant AND IsAllergic = :isAllergic AND IsCustomized = :isCustomized AND IsCommon = :isCommon AND PackageDetailsId = :packageDetailsId", nativeQuery = true)
	Optional<Integer> getPackageTypeId(@Param("isPregnant") boolean isPregnant,
	                                   @Param("isAllergic") boolean isAllergic,
	                                   @Param("isCustomized") boolean isCustomized,
	                                   @Param("isCommon") boolean isCommon,
	                                   @Param("packageDetailsId") int packageDetailsId);



}
