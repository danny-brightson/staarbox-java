package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDatailsDTO;
import com.example.demo.dto.SaveCustomerFeedBackDTO;
import com.example.demo.entity.DailyTagLine;
import com.example.demo.repo.CustomerFeedbackRepo;
import com.example.demo.repo.DailyTaglineRepo;
import com.example.demo.service.CustomerFeedbackService;

@RestController
@RequestMapping("/api")
public class CustomerFeedBackRest {

	 @Autowired
	    private  CustomerFeedbackService  customerFeedbackService;
	 
	 @PostMapping("/CustomerFeedBack")
	    public boolean saveCustomerFeedBack(@RequestBody SaveCustomerFeedBackDTO request) {
	        boolean res = false;
			try {
				res = customerFeedbackService.saveCustomerFeedBack(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         return res;
	    }
	
}
