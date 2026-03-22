package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LocationRequestDto;
import com.example.demo.dto.LocationResponseDto;
import com.example.demo.dto.addressChangeDto;
import com.example.demo.service.CustomerDeatilService;
import com.example.demo.service.DeliveryBoyLocationService;

@RestController
@RequestMapping("/api/location")
public class DeliveryBoyLocationController {

    @Autowired
    private DeliveryBoyLocationService service;
    
    @Autowired
	private CustomerDeatilService customerDeatilService;

    @PostMapping("/update")
    public ResponseEntity<String> updateLocation(@RequestBody LocationRequestDto dto) {
        service.updateLocation(dto);
        return ResponseEntity.ok("Location updated and broadcasted via WebSocket");
    }

    @GetMapping("/get/{phoneNumber}")
    public ResponseEntity<LocationResponseDto> getLocation(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(service.getLocation(phoneNumber));
    }
    
	@PutMapping("/UpdateAddress")
	public boolean UpdateAddressDetails(@RequestBody addressChangeDto request) {

		boolean	response = customerDeatilService.UpdateLocationAddress(request);
		
		
		return response;
		
	}
}
