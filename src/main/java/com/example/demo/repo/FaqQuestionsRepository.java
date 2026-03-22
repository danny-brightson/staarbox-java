package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FaqQuestions;

@Repository
public interface FaqQuestionsRepository extends JpaRepository<FaqQuestions, Long> {

    List<FaqQuestions> findByStatusId(Long statusId);
}

