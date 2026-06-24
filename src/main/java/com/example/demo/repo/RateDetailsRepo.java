package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PackageType;
import com.example.demo.entity.RateDetails;

@Repository
public interface RateDetailsRepo extends JpaRepository<RateDetails, Long>  {

}
