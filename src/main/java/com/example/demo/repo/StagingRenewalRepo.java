package com.example.demo.repo;

import com.example.demo.entity.StagingRenewal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface StagingRenewalRepo extends JpaRepository<StagingRenewal, Long> {

    Optional<StagingRenewal> findByCustomerId(Long customerId);

    List<StagingRenewal> findByProcessedFalse();
}