package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Partner;

@Repository
public interface PartnerRepo extends JpaRepository<Partner, Long> {
}