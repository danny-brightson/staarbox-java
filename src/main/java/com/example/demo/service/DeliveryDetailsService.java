package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.TodaysDeliveryDetails;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.TodaysDeliveryDetailsRepo;

import jakarta.transaction.Transactional;

@Service
public class DeliveryDetailsService {

	@Autowired
	private TodaysDeliveryDetailsRepo todaysDeliveryDetailsRepo;
	
	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	public Boolean deliveryBoyScan(int districtId, long boxnumber, String phoneNumber) {
		Optional<TodaysDeliveryDetails> optionalData = 
			    todaysDeliveryDetailsRepo.findByDistrictIdAndBoxnumber(districtId, boxnumber);

			if (optionalData.isPresent()) {
			
			    todaysDeliveryDetailsRepo.updateDeliveryBoyDetils(optionalData.get().getId(), phoneNumber);
			    return true;
			} else {
			    return false;
			}

	}

	public List<TodaysDeliveryDetails> DeiliveryBoyList(String phoneNumber) {
		List<TodaysDeliveryDetails> data = todaysDeliveryDetailsRepo.findByPhoneNumber(phoneNumber);
		
		
		return data;
	}


	public Boolean UpdateDeliverdStatus(long boxnumber, String phoneNumber, boolean isDelivered, Integer reasonId) {

	    Optional<TodaysDeliveryDetails> data = todaysDeliveryDetailsRepo.findByDeliveryboyPhoneNumberAndBoxNumber(phoneNumber, boxnumber);
	    
	    if (data.isPresent()) {
	        Long id = data.get().getId();
	        Integer finalReasonId = isDelivered ? null : reasonId;
	    	Boolean isCustomized = false;

	        // If not delivered and no reason provided, reject the request
	        if (!isDelivered && reasonId == null) {
	            throw new IllegalArgumentException("Reason ID must be provided if not delivered.");
	        }

	        todaysDeliveryDetailsRepo.updateDeliveredStatus(id, isDelivered, finalReasonId,LocalDateTime.now(),"DeliveryPerson");
	        customerDetailsRepo.updateCustomization(
	        		data.get().getCustomerId(),
					isCustomized,
					LocalDateTime.now(),
				    "user",
				    LocalDate.now()
				);
	        return true;
	    } else {
	        return false;
	    }
	}

	
	
}
