package com.example.demo.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StagingPackaging;

@Repository
    public interface StagingPackagingRepo extends JpaRepository<StagingPackaging, Long> {
        boolean existsByCustomerId(Long customerId);
        boolean existsByCustomerIdAndCreatedTimeBetween(
        Long customerId,
        LocalDateTime start,
        LocalDateTime end
    );
}