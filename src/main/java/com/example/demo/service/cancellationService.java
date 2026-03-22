package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CancelledDate;
import com.example.demo.repo.cancelledDateRepo;

@Service
public class cancellationService {

	
	
	

	    @Autowired
	    private cancelledDateRepo cancelledDateRepository;

	    public String handleCancellation(Long custId,
	                                     List<LocalDate> cancelledDates,
	                                     Boolean isCancelled) {

	        for (LocalDate date : cancelledDates) {

	            LocalDateTime cancelledDateTime = date.atStartOfDay();

	            if (isCancelled) {

	                // 🔹 Insert new cancellation
	                CancelledDate cancel = new CancelledDate();
	                cancel.setCustomerId(custId);
	                cancel.setCancelledDate(cancelledDateTime);
	                cancel.setStatusId(1L); // Cancelled
	                cancel.setCreatedBy("User");
	                cancel.setCreatedTime(LocalDateTime.now());

	                cancelledDateRepository.save(cancel);

	            } else {

	                // 🔹 Revoke cancellation
	                Optional<CancelledDate> existing =
	                        cancelledDateRepository
	                                .findByCustomerIdAndCancelledDate(custId, cancelledDateTime);

	                if (existing.isPresent()) {
	                    CancelledDate cancel = existing.get();
	                    cancel.setStatusId(2L); // Revoked
	                    cancel.setModefiedBy("User");
	                    cancel.setModefiedTime(LocalDate.now());

	                    cancelledDateRepository.save(cancel);
	                }

	            }
	        }

	        return isCancelled ? "Cancelled Successfully" : "Revoked Successfully";
	    }
	    
	    public List<LocalDateTime> getCancelledDates(Long customerId) {
	        return cancelledDateRepository.findActiveCancelledDates(customerId);
	    }
	}


