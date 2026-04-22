package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DeliveryPersonResponseDto;
import com.example.demo.entity.TodaysDeliveryDetails;
import com.example.demo.service.DeliveryDetailsService;

@RestController
@RequestMapping("/api")
public class DeliveryDetailsRest {

	
    @Autowired
    private  DeliveryDetailsService  deliveryDetailsService;
    
	 @PutMapping("/DeiliveryBoyScan")
	    public Boolean DeiliveryBoyScan(@RequestParam int districtId,@RequestParam long boxnumber,@RequestParam String phoneNumber) {
		 return deliveryDetailsService.deliveryBoyScan(districtId,boxnumber,phoneNumber);
	    }
	 
	 @GetMapping("/DeiliveryBoyBoxList")
	    public List<TodaysDeliveryDetails> DeiliveryBoyList(@RequestParam String phoneNumber) {
		 return deliveryDetailsService.DeiliveryBoyList(phoneNumber);
	    }
	 
	 @PutMapping("/UpdateDeliverdStatus")
	    public Boolean UpdateDeliverdStatus(@RequestParam long boxnumber,@RequestParam String phoneNumber,
	    		@RequestParam boolean isDelivered,@RequestParam(required = false) int reasonId) {
		 return deliveryDetailsService.UpdateDeliverdStatus(boxnumber,phoneNumber,isDelivered,reasonId);
	    }

	@GetMapping("/getDeliveryPersonDetails")
	public DeliveryPersonResponseDto getDeliveryPersonDetails(
			@RequestParam("PhoneNumber") String phoneNumber) {
		return deliveryDetailsService.getDeliveryPersonDetails(phoneNumber);
	}
}
