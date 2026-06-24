package com.example.demo.repo;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByCustomerId(Long customerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Wallet SET Amount = :amount WHERE CustomerId = :customerId", nativeQuery = true)
    int updateWalletAmount(@Param("customerId") Long customerId,
                           @Param("amount") BigDecimal amount);
}

