package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StagingRenewal;

@Repository
public interface StagingRenewalRepo extends JpaRepository<StagingRenewal, Long> {

    Optional<StagingRenewal> findByCustomerId(Long customerId);

    List<StagingRenewal> findByProcessedFalse();
}