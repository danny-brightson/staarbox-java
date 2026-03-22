package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.ReasonForNotDelivered;

@Repository
public interface ReasonForNotDeliveredRepo extends JpaRepository<ReasonForNotDelivered , Long> {

}
