package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomerPromo;

@Repository
public interface CustomerPromoRepository extends JpaRepository<CustomerPromo, Long> {

    boolean existsByCustomerIdAndPromoCodeId(Long customerId, Long promoCodeId);

}
