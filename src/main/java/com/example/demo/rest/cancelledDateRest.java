package com.example.demo.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.cancellationService;


@RestController
@RequestMapping("/api")
public class cancelledDateRest {

	
	  @Autowired
	    private cancellationService cancellationService;
	
	@PostMapping("/cancelDate")
	public ResponseEntity<String> cancelOrRevoke(
	        @RequestParam Long custId,
	        @RequestParam List<String> cancelledDates,
	        @RequestParam Boolean isCancelled) {

	    List<LocalDate> parsedDates = cancelledDates.stream()
	            .map(LocalDate::parse)
	            .toList();

	    String response = cancellationService
	            .handleCancellation(custId, parsedDates, isCancelled);

	    return ResponseEntity.ok(response);
	}

	   @GetMapping("/getCancelledDate")
	    public ResponseEntity<List<LocalDate>> 
	        getCancelledDates(@RequestParam Long customerId) {

	        return ResponseEntity.ok(
	        		cancellationService.getCancelledDates(customerId));
	    }
}
