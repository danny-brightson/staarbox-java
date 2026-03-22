package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.KidPackDetails;

@Repository
public interface KidPackDetailsRepo extends JpaRepository<KidPackDetails, Long>{

}
