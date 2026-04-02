package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StagingRenewal;

@Repository
public interface StagingRenewalRepo extends JpaRepository<StagingRenewal, Long> {

    Optional<StagingRenewal> findByCustomerId(Long customerId);

    List<StagingRenewal> findByProcessedFalse();
    
    @Query("SELECT s FROM StagingRenewal s WHERE s.customerId = :customerId AND s.processed = false ORDER BY s.id DESC")
    Optional<StagingRenewal> findLatestUnprocessed(@Param("customerId") Long customerId);
}