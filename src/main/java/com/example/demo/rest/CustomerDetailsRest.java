package com.example.demo.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.CustomerDatailsDTO;
import com.example.demo.dto.CustomerResponseDto;
import com.example.demo.dto.CustomerSaverRequestDto;
import com.example.demo.dto.CustomerwebsiteresDto;
import com.example.demo.dto.RenewalDateResponse;
import com.example.demo.dto.addressChangeDto;
import com.example.demo.dto.packageDetailsSaveRequest;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.service.CustomerDeatilService;
import com.example.demo.service.PincodeDetailsService;
import com.example.demo.service.SubscriptionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class CustomerDetailsRest {

	@Autowired
	private CustomerDeatilService customerDeatilService;

	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;
	@Autowired
	private PincodeDetailsService pincodeDetailsService;

	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping("/subscription/update-pack-details")
	public ResponseEntity<String> updatePack(
			@RequestParam Long customerId,
			@RequestParam Long newPackId) {

		String response = subscriptionService.updatePackDetails(customerId, newPackId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/save")
	public ResponseEntity<?> createCustomer(@RequestBody List<CustomerDatailsDTO> customer) {
		List<CustomerResponseDto>  response = null;
		try {
			response = customerDeatilService.SaveCustomerDetails(customer);
			System.out.println(response);
			 if (response == null) {
		            // Return 400 Bad Request with custom message
		            return ResponseEntity
		                .status(HttpStatus.BAD_REQUEST)
		                .body("Customer creation failed: required data (like phone number) might be missing.");
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
	

	@GetMapping("/customerDetails")
	public Optional<CustomerDetails> getCustomerDetails(@RequestParam long CustomerId) {
		//LocalDateTime  validDate = LocalDateTime .now().minusDays(28); // 28-day validity rule
	    return customerDetailsRepo.findById(CustomerId);
	}
	
	@PostMapping("/savePackDetails")
	public List<Map<String, Object>> savePackDetails(@RequestBody List<packageDetailsSaveRequest> request) {

		List<Map<String, Object>>	response = customerDeatilService.savePackDetails(request);
		
		
		return response;
		
	}
	
	@PutMapping("/UpdateAddress")
	public String UpdateAddressDetails(@RequestBody addressChangeDto request) {

		String	response = customerDeatilService.UpdateAddressDetails(request);
		
		
		return response;
		
	}
	
	@GetMapping("/checkCustomaizationEnable")
	public boolean checkCustomaizationEnable(@RequestParam int CustomerId ) {
	boolean	response = customerDeatilService.checkCustomaizationEnable(CustomerId);		
	return response;
		
	}
	
	@GetMapping("/getNextRenewaldate")
	public ResponseEntity<RenewalDateResponse> getNextRenewaldate(@RequestParam int CustomerId ) {
		Optional<CustomerDetails> data = customerDetailsRepo.findById((long) CustomerId);	
			RenewalDateResponse dto = new RenewalDateResponse(data.get().getPaymentDoneTime(),data.get().getNextrenewalDate() ,data.get().getPackDetailsId());
	            return ResponseEntity.ok(dto);	
	}

    @PostMapping("/checkout")
    public ResponseEntity<?> receiveCheckout(@RequestBody CustomerSaverRequestDto data) throws IOException {
        System.out.println("Checkout received: " + data.firstName + ", Plan: " + data.planName);
	    	Boolean isValidPincode = false;
	    	try {
	    		isValidPincode = pincodeDetailsService.CheckPinCodeAvailability(data.getPincode());
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	      	CustomerwebsiteresDto response = new CustomerwebsiteresDto();
	    	  if (!isValidPincode) {
	    	        return ResponseEntity
	    	                .badRequest()
	    	                .body("We’re sorry! Staarbox delivery is not yet available in your area. We will be serving your location soon.");
	    	    }
	    	  else {
	    		  response =customerDeatilService.saveCheckoutCustomerDetails(data);
	    	        return ResponseEntity.ok(response);
	    	  }
    }
    
    @GetMapping("/host-check")
    public String checkHost(HttpServletRequest request) {
        String host = request.getHeader("Host");
        System.out.println("Received Host Header: " + host);
        return "Host Header: " + host;
    }
    
    @GetMapping("/check-ip")
    public String getClientIp(HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        return "X-Real-IP: " + realIp + "\n" +
               "X-Forwarded-For: " + forwardedFor + "\n" +
               "RemoteAddr: " + remoteAddr;
    }
    
    @GetMapping("/check-proto")
    public String checkProto(HttpServletRequest request) {
        String proto = request.getHeader("X-Forwarded-Proto");
        return "X-Forwarded-Proto: " + proto;
    }
    
    @PutMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestParam Long customerId) {
        CustomerDetails c = customerDetailsRepo.findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        // 1) Never requested cancellation
        if (c.getCancelRequestedDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Please request subscription cancellation first.");
        }

        // 2) Requested but pending (not reached scheduled date)
        LocalDate now = LocalDate.now();
        if (!c.getIsCancelled() && c.getCancelScheduledDate() != null && now.isBefore(c.getCancelScheduledDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Subscription cancellation pending. Deletion allowed after: " + c.getCancelScheduledDate());
        }

        // 3) Cancellation completed (isCancelled == true or scheduled date reached)
        // If you want to allow deletion the moment scheduled date reached even without a background job,
        // treat scheduledDate <= today as cancelled.
        boolean effectiveCancelled = c.getIsCancelled() || (c.getCancelScheduledDate() != null && !now.isBefore(c.getCancelScheduledDate()));
        if (!effectiveCancelled) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Subscription not cancelled yet. Please wait until cancellation completes.");
        }

        // perform deletion/update
        int result = customerDetailsRepo.updateCustomerStatus(customerId); // your existing update method
        if (result > 0) {
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed. Try again.");
        }
    }

	
	@PostMapping("/cancelSubscription")
	public ResponseEntity<String> cancelSubscription(@RequestParam Long customerId) {
		    CustomerDetails c = customerDetailsRepo.findById(customerId)
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		    if (c.getIsCancelled()) {
		        return ResponseEntity.badRequest().body("Subscription already cancelled.");
		    }

		    LocalDate today = LocalDate.now();
		    c.setCancelRequestedDate(today);
		    c.setCancelScheduledDate(today.plusDays(2));
		    // keep isCancelled = false for now
		    customerDetailsRepo.save(c);

		    return ResponseEntity.ok("Cancellation requested. Subscription will end on " + c.getCancelScheduledDate());
		}
	@PutMapping("/delete-customer")
    public ResponseEntity<String> deleteCustomerId(@RequestParam Long customerId) {
        CustomerDetails c = customerDetailsRepo.findById(customerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        int result = customerDetailsRepo.updateCustomerStatus(customerId); // your existing update method
        if (result > 0) {
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed. Try again.");
        }
	}

}
