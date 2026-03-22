package com.example.demo.rest;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerStatusDto;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.LkpCustomerStatus;
import com.example.demo.entity.ReasonForNotDelivered;
import com.example.demo.entity.RefreshToken;
import com.example.demo.projection.AvailableDistrictProjection;
import com.example.demo.projection.DeliveryTimingProjection;
import com.example.demo.projection.GenderProjection;
import com.example.demo.projection.PackDetailsProjection;
import com.example.demo.projection.StateProjection;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.LkpAvailableDistrictRepo;
import com.example.demo.repo.LkpCustomerStatusRepo;
import com.example.demo.repo.LkpDeliveryTimingsRepo;
import com.example.demo.repo.LkpGenderRepo;
import com.example.demo.repo.LkpPackDetailsRepo;
import com.example.demo.repo.LkpStateRepo;
import com.example.demo.repo.ReasonForNotDeliveredRepo;
import com.example.demo.repo.RefreshTokenRepo;


@RestController
@RequestMapping("/api")
public class LkpTablesController {

	
	@Autowired
	private LkpDeliveryTimingsRepo lkpDeliveryTimingsRepo;

	@Autowired
	private LkpAvailableDistrictRepo lkpAvailableDistrictRepo;
	
	@Autowired
	private LkpPackDetailsRepo lkpPackDetailsRepo;
	
	@Autowired
	private LkpStateRepo lkpStateRepo;
	
	@Autowired
	private LkpGenderRepo lkpGenderRepo;
	
	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;
	
	
	@Autowired
	private LkpCustomerStatusRepo lkpCustomerStatusRepo;
	
	@Autowired
	private RefreshTokenRepo refreshTokenRepo;
	
	@Autowired
	private ReasonForNotDeliveredRepo reasonForNotDeliveredRepo;
	
	@GetMapping("/deliveryTiming")
	public List<DeliveryTimingProjection> getDeliveryTimings() {
		return lkpDeliveryTimingsRepo.getDeliveryTimings();
	}

	@GetMapping("/availableDistrict")
	public List<AvailableDistrictProjection> getAvailableDistrict() {
		return lkpAvailableDistrictRepo.getAvailableDistrict();
	}
	
	@GetMapping("/packageDetails")
	public ResponseEntity<?> getPackDetails(@RequestParam int customerId) {
	    
	    int districtId = customerDetailsRepo.getDistrictId(customerId);
	    boolean isPragnent = customerDetailsRepo.getIsPragnentFlag(customerId);

	    if (districtId == 0) {
	        return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body("Package is not available for the districtId");
	    }

	    List<PackDetailsProjection> response = lkpPackDetailsRepo.getPackDetails(districtId);

	    if (isPragnent) {
	        List<PackDetailsProjection> filtered = response.stream()
	            .filter(pkg -> !"KidPack".equalsIgnoreCase(pkg.getPackageName()))
	            .collect(Collectors.toList());

	        return ResponseEntity.ok(filtered); // ✅ wrap properly
	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/gender")
	public List<GenderProjection> getGender() {
		return lkpGenderRepo.getGender();
	}
	
	@GetMapping("/state")
	public List<StateProjection> getState() {
		return lkpStateRepo.getState();
	}
	
	@GetMapping("/CheckStatus")
	public ResponseEntity<?> checkStatus(@RequestParam String refreshToken) {

	    Optional<RefreshToken> refreshOpt =
	            refreshTokenRepo.findByResfreshToken(refreshToken);

	    if (refreshOpt.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Invalid refresh token");
	    }

	    String phoneNumber = refreshOpt.get().getPhoneNumber();

	    // ✅ Get all pending customer IDs for this phone number
	    List<Integer> pendingCustomerIds =
	            customerDetailsRepo.getPendingCustomerid(phoneNumber);

	    if (pendingCustomerIds == null || pendingCustomerIds.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("No pending customers found");
	    }

	    List<CustomerStatusDto> responseList = new ArrayList<>();

	    for (Integer customerId : pendingCustomerIds) {

	        Optional<CustomerDetails> custOpt =
	                customerDetailsRepo.findById(Long.valueOf(customerId));

	        if (custOpt.isEmpty()) {
	            continue;
	        }

	        CustomerDetails customer = custOpt.get();
	        Long statusId = Long.valueOf(customer.getCustomerStatusId());

	        Optional<LkpCustomerStatus> statusOpt =
	                lkpCustomerStatusRepo.findById(statusId);

	        if (statusOpt.isPresent()) {
	            String statusName = statusOpt.get().getName();

	            responseList.add(
	                new CustomerStatusDto(
	                    customer.getId(),
	                    statusId,
	                    statusName
	                )
	            );
	        }
	    }

	    if (responseList.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body("Status not found for pending customers");
	    }

	    return ResponseEntity.ok(responseList);
	}

//	public ResponseEntity<?> checkStatus(@RequestParam String refreshToken) {
//	    Optional<RefreshToken> refresh = refreshTokenRepo.findByResfreshToken(refreshToken);
//
//	    if (refresh.isPresent()) {
//	        String phoneNumber = refresh.get().getPhoneNumber();
//	        Optional<CustomerDetails> custD = customerDetailsRepo
//	                .findTopByPhoneNumberOrderByCreatedTimeDesc(phoneNumber);
//
//	        Long customerId = null;
//	        Long statusId;
//
//	        if (custD.isPresent()) {
//	            CustomerDetails customer = custD.get();
//	            customerId = customer.getId();
//	            statusId = Long.valueOf(customer.getCustomerStatusId());
//	        } else {
//	            statusId = 1L; // default status if customer not found
//	        }

//	        Optional<LkpCustomerStatus> statusOpt = lkpCustomerStatusRepo.findById(statusId);
//	        if (statusOpt.isPresent()) {
//	            String statusName = statusOpt.get().getName();
//	            CustomerStatusDto dto = new CustomerStatusDto(customerId, statusId, statusName);
//	            return ResponseEntity.ok(dto);
//	        } else {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status not found");
//	        }
//	    }
//
//	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
//	}

	@GetMapping("/lkpReasonForNotDelivered")
	public List<ReasonForNotDelivered> lkpReasonForNotDelivered() {
		return reasonForNotDeliveredRepo.findAll();
	}
}