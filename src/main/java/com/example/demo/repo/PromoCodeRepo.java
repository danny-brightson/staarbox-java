package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PromoCode;

@Repository
public interface PromoCodeRepo extends JpaRepository<PromoCode, Long> {

    
    @Query(value = """
        SELECT * FROM promocode 
        WHERE Code = :code 
        AND IsActive = TRUE 
        AND UsedCount < UsageLimit
        AND (ExpiryDate IS NULL OR ExpiryDate >= CURDATE())
        LIMIT 1
    """, nativeQuery = true)
    Optional<PromoCode> findValidPromo(@Param("code") String code);

    // UsedCount increment

    @Modifying
    @Transactional
    @Query(value = "UPDATE promocode SET UsedCount = UsedCount + 1 WHERE Code = :code", nativeQuery = true)
    void incrementUsedCount(@Param("code") String code);
}