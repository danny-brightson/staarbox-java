package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.LkpDeliveryTimings;
import com.example.demo.entity.LkpFruitAndNuts;

@Repository
public interface LkpFruitAndNutsRepo extends JpaRepository<LkpFruitAndNuts, Long> {

	@Query(value = "SELECT * FROM lkpfruitandnuts WHERE LOWER(FruitAndNuts) = LOWER(:fruitAndNuts) LIMIT 1", nativeQuery = true)
	Optional<LkpFruitAndNuts> findByFruitAndNutsIgnoreCase(@Param("fruitAndNuts") String fruitAndNuts);


}
