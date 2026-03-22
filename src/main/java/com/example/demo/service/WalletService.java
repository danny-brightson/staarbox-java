package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RecentActivityResponse;
import com.example.demo.entity.Wallet;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.WalletRepository;

@Service
public class WalletService {

	@Autowired
    private WalletRepository walletRepository;

	@Autowired
    private CustomerDetailsRepo customerDetailsRepo;

	public RecentActivityResponse getRecentActivity(Long customerId) {

	    Wallet wallet = walletRepository.findByCustomerId(customerId).orElse(null);

	    LocalDateTime renewalData = customerDetailsRepo.findRenewalDetails(customerId);

	    LocalDateTime renewedDate = renewalData != null ? renewalData : null;

	    return new RecentActivityResponse(
	            wallet != null ? wallet.getLastpaidAmount() : null,
	            wallet != null ? wallet.getLastPaymentDate() : null,
	            wallet != null ? wallet.getLastCustomaizedDate() : null,
	            renewedDate
	    );
	}

	    public BigDecimal getWalletBalance(Long customerId) {

	        Optional<Wallet> walletOpt = walletRepository.findByCustomerId(customerId);

	        // 🔹 If wallet exists
	        if (walletOpt.isPresent()) {

	            BigDecimal amount = walletOpt.get().getAmount();

	            return amount != null ? amount : BigDecimal.ZERO;
	        }

	        // 🔹 If wallet does NOT exist
	        BigDecimal rate = customerDetailsRepo.findRateByCustomerId(customerId);

	      
	        return rate != null ? rate : BigDecimal.ZERO;
	    }

	public void customizeWallet(Long customerId, BigDecimal amount, LocalDateTime date) {

		Optional<Wallet> walletOpt = walletRepository.findByCustomerId(customerId);

		
		BigDecimal rate = customerDetailsRepo.findRateByCustomerId(customerId);

		BigDecimal lastPaidAmount = rate != null ? rate : null;

		if(walletOpt.isPresent()) {

			Wallet wallet = walletOpt.get();

			wallet.setLastCustomaizedDate(date);
			wallet.setLastCustomizedAmount(amount);
			//wallet.setLastpaidAmount(lastPaidAmount);

			walletRepository.save(wallet);

		} 
		else {

			Wallet wallet = new Wallet();

			wallet.setCustomerId(customerId);
			wallet.setAmount(lastPaidAmount);
			wallet.setLastCustomaizedDate(date);
			wallet.setLastCustomizedAmount(amount);
			wallet.setLastpaidAmount(lastPaidAmount);

			walletRepository.save(wallet);
		}
	}
	
}