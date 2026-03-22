package com.example.demo.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CancelledDate;

@Repository
public interface cancelledDateRepo extends JpaRepository<CancelledDate, Long>  {


	 boolean existsByCustomerIdAndCancelledDate(Long customerId, LocalDateTime cancelledDate);

	 Optional<CancelledDate> findByCustomerIdAndCancelledDate(Long customerId, LocalDateTime cancelledDate);

	 @Query("SELECT c.cancelledDate " +
	           "FROM CancelledDate c " +
	           "WHERE c.customerId = :customerId " +
	           "AND c.statusId = 1")
	    List<LocalDateTime> findActiveCancelledDates(
	            @Param("customerId") Long customerId);
	
	 
	}

