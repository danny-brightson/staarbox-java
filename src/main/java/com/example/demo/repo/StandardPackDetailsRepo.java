package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StandardPackDetails;

@Repository
public interface StandardPackDetailsRepo extends JpaRepository<StandardPackDetails, Long>{

}
