package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.DailyTagLine;

@Repository
public interface DailyTaglineRepo extends JpaRepository<DailyTagLine, Long> {

	//DailyTagLine findTopByOrderByCreatedTimeDesc();

}
