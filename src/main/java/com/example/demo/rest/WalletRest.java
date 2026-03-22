package com.example.demo.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RecentActivityResponse;
import com.example.demo.service.WalletService;

@RestController
@RequestMapping("/api")
public class WalletRest  {
	
	
	
	@Autowired
    private WalletService walletService;


	@GetMapping("/recentActivity")
	public ResponseEntity<RecentActivityResponse> recentActivity(
	        @RequestParam Long customerId) {

	    RecentActivityResponse response = walletService.getRecentActivity(customerId);

	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("/wallet/balance")
	public ResponseEntity<BigDecimal> getWalletBalance(
	        @RequestParam Long customerId) {

	    BigDecimal balance = walletService.getWalletBalance(customerId);

	    return ResponseEntity.ok(balance);
	}

	@PostMapping("/wallet/customize")
	public ResponseEntity<String> customizeWallet(
        @RequestParam Long customerId,
        @RequestParam BigDecimal amount) {

		walletService.customizeWallet(customerId, amount, LocalDateTime.now());

		return ResponseEntity.ok("Wallet updated successfully");
	}
} 
