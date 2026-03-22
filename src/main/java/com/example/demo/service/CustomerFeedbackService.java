package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SaveCustomerFeedBackDTO;
import com.example.demo.entity.CustomerFeedback;
import com.example.demo.repo.CustomerFeedbackRepo;

@Service
public class CustomerFeedbackService {
	
	@Autowired
    private  CustomerFeedbackRepo  customerFeedbackRepo;

	public  boolean saveCustomerFeedBack(SaveCustomerFeedBackDTO request) {
		CustomerFeedback customerFeedback = new CustomerFeedback();
		customerFeedback.setCustomerId(request.getCustomerId());
		customerFeedback.setFeedBack(request.getFeedBack());
		customerFeedback.setStarRating(request.getStarRating());
		customerFeedback.setStatusId((long) 1);
		customerFeedback.setCreatedBy("User");
		customerFeedback.setCreatedTime(LocalDateTime.now());
		
		CustomerFeedback saved = customerFeedbackRepo.save(customerFeedback);
		
		if(saved.getId() != null) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
