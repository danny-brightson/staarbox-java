package com.example.demo.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.entity.TodaysDeliveryDetails;


import jakarta.transaction.Transactional;

@Repository
public interface TodaysDeliveryDetailsRepo extends JpaRepository<TodaysDeliveryDetails, Long> {

    // =========================================================
    // GET BY DISTRICT + BOXNUMBER + BUSINESS DATE
    // =========================================================
    @Query(value = """
            SELECT * 
            FROM todaysdeliverydetails  
            WHERE StatusId = 1 
              AND DistrictId = :districtId 
              AND BoxNumber = :boxnumber
              AND BusinessDate = CURDATE()
            """, nativeQuery = true)
    Optional<TodaysDeliveryDetails> findByDistrictIdAndBoxnumber(
            @Param("districtId") int districtId,
            @Param("boxnumber") long boxnumber
    );


    // =========================================================
    // UPDATE DELIVERY BOY DETAILS WITH BUSINESS DATE
    // =========================================================
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE todaysdeliverydetails 
            SET DeliveryboyPhoneNumber = :phoneNumber 
            WHERE Id = :id 
              AND StatusId = 1
              AND BusinessDate = CURDATE()
            """, nativeQuery = true)
    int updateDeliveryBoyDetils(
            @Param("id") Long id,
            @Param("phoneNumber") String phoneNumber
    );


    // =========================================================
    // GET ALL BY PHONE NUMBER + BUSINESS DATE
    // =========================================================
    @Query(value = """
            SELECT * 
            FROM todaysdeliverydetails  
            WHERE StatusId = 1 
              AND DeliveryboyPhoneNumber = :phoneNumber
              AND BusinessDate = CURDATE()
            """, nativeQuery = true)
    List<TodaysDeliveryDetails> findByPhoneNumber(
            @Param("phoneNumber") String phoneNumber
    );


    // =========================================================
    // UPDATE DELIVERY STATUS + DELIVERY TIME
    // =========================================================
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE todaysdeliverydetails 
            SET IsDelivered = :isDelivered,
                ReasonForNotDelivered = :reasonId,
                DeliveredTime = :deliveredTime,
                ModefiedTime = :modifiedTime,
                ModefiedBy = :modifiedBy
            WHERE Id = :id
              AND BusinessDate = CURDATE()
            """, nativeQuery = true)
    int updateDeliveredStatus(
            @Param("id") Long id,
            @Param("isDelivered") Boolean isDelivered,
            @Param("reasonId") Integer reasonId,
            @Param("deliveredTime") LocalDateTime deliveredTime,
            @Param("modifiedTime") LocalDateTime modifiedTime,
            @Param("modifiedBy") String modifiedBy
    );


    // =========================================================
    // FIND BY DELIVERY BOY + BOXNUMBER + BUSINESS DATE
    // =========================================================
    @Query(value = """
            SELECT * 
            FROM todaysdeliverydetails
            WHERE DeliveryboyPhoneNumber = :phoneNumber
              AND BoxNumber = :boxnumber
              AND BusinessDate = CURDATE()
            """, nativeQuery = true)
    Optional<TodaysDeliveryDetails> findByDeliveryboyPhoneNumberAndBoxNumber(
            @Param("phoneNumber") String phoneNumber,
            @Param("boxnumber") long boxnumber
    );

}