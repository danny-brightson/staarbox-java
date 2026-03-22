package com.example.demo.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OtpVerificationRequest;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repo.RefreshTokenRepo;

@Service
public class RefreshTokenService {

	
	 @Value("${app.jwt.refreshTokenDurationMs}")
	    private Long refreshTokenDurationMs;

	    @Autowired
	    private RefreshTokenRepo refreshTokenRepo;

	    public RefreshToken createRefreshToken(String phoneNumber) {
	        RefreshToken token = new RefreshToken();
	        token.setPhoneNumber(phoneNumber);;
	        token.setResfreshToken(UUID.randomUUID().toString());
	        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
	        token.setIsTokenValid(true);
	        token.setStatusId((long) 1);
	        token.setCreatedBy("user");
	        token.setCreatedTime(LocalDateTime.now());
	        return refreshTokenRepo.save(token);
	    }

	    public Optional<RefreshToken> validateToken(String token) {
	        return refreshTokenRepo.findByResfreshToken(token)
	                .filter(rt -> rt.getIsTokenValid() && rt.getExpiryDate().isAfter(Instant.now()));
	    }
	    
	    public void invalidateToken(String tokenStr) {
	    	refreshTokenRepo.findByResfreshToken(tokenStr).ifPresent(token -> {
	            token.setIsTokenValid(false);
	            refreshTokenRepo.save(token);
	        });
	    }


}
