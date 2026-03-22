package com.example.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.OtpVerificationRequest;
import com.example.demo.dto.TokenResponse;
import com.example.demo.entity.PackageUserDetails;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.DeliveryPersonDetailsRepo;
import com.example.demo.repo.PackageUserDetailsRepo;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@Autowired
	private DeliveryPersonDetailsRepo deliveryPersonDetailsRepo;
	
	@Autowired
	private PackageUserDetailsRepo  packageUserDetailsRepo;
	
	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	@PostMapping("/verify-otp")
	public ResponseEntity<TokenResponse> verifyOtp(@RequestBody OtpVerificationRequest request) {
		boolean isDeliveryPerson = false;
		// Step 2: Create user if not exists
		String phoneNumber = deliveryPersonDetailsRepo.findyPhoneNumber(request.getPhoneNumber());
		if (phoneNumber != null) {
			 isDeliveryPerson = true;
		}

		// Step 3: Create tokens
		String accessToken = jwtUtil.generateAccessToken(request.getPhoneNumber());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getPhoneNumber());
		List<Integer> customerId = customerDetailsRepo.getCustomerid(request.getPhoneNumber());
		List<Integer> PendingCustomerId = customerDetailsRepo.getPendingCustomerid(request.getPhoneNumber());
		//System.out.println(customerId);

		// Step 4: Return tokens
		return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken.getResfreshToken(),isDeliveryPerson,customerId,PendingCustomerId));
	}

	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
		String refreshTokenStr = request.get("refreshToken");

		return refreshTokenService.validateToken(refreshTokenStr).map(token -> {
			String phoneNumber = token.getPhoneNumber();
			String newAccessToken = jwtUtil.generateAccessToken(phoneNumber);
			return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
		}).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("error", "Invalid or expired refresh token")));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
		refreshTokenService.invalidateToken(request.get("refreshToken"));
		return ResponseEntity.ok("Logged out successfully.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String userName,@RequestParam String passWord) {
		
		PackageUserDetails  user = packageUserDetailsRepo.findByUserNameAndPassword(userName, passWord);
		if(user != null) {
			String phoneNumber = passWord;
			String accessToken = jwtUtil.generateAccessToken(phoneNumber);
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(phoneNumber);
		System.out.println(user.getId());
		return ResponseEntity.ok(new LoginResponse(user.getId(),user.getDistrictId(),accessToken, refreshToken.getResfreshToken(),user.getIsVerifier()));
		}
		else {
			 return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Invalid username or password");
		}
	}
	
}
