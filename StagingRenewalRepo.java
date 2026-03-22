package com.example.demo.repo;

import com.example.demo.entity.StagingRenewal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StagingRenewalRepo extends JpaRepository<StagingRenewal, Long> {

    Optional<StagingRenewal> findByCustomerId(Long customerId);
}