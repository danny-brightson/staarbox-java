package com.example.demo.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.CustomerDatailsDTO;
import com.example.demo.dto.CustomerResponseDto;
import com.example.demo.dto.CustomerSaverRequestDto;
import com.example.demo.dto.CustomerwebsiteresDto;
import com.example.demo.dto.addressChangeDto;
import com.example.demo.dto.packageDetailsSaveRequest;
import com.example.demo.entity.CheckoutData;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.LkpAvailablePincodes;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repo.CheckoutDataRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.DeliveryPersonDetailsRepo;
import com.example.demo.repo.LkpAvailablePincodesRepo;
import com.example.demo.repo.RefreshTokenRepo;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.QRCodeGenerator;

@Service
public class CustomerDeatilService {

	private final QRCodeGenerator QRCodeGenerator;

	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	@Autowired
	private CheckoutDataRepo checkoutDataRepo;

	@Autowired
	private LkpAvailablePincodesRepo lkpAvailablePincodesRepo;

	@Autowired
	private DeliveryPersonDetailsRepo deliveryPersonDetailsRepo;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	CustomerDeatilService(QRCodeGenerator QRCodeGenerator) {
		this.QRCodeGenerator = QRCodeGenerator;
	}

//	public CustomerResponseDto SaveCustomerDetails(CustomerDatailsDTO CustomerDatailsDTO) throws IOException {
//		
//		Optional<RefreshToken> refreshToken = refreshTokenRepo.findTopByPhoneNumberOrderByCreatedTimeDesc(CustomerDatailsDTO.getPhoneNumber());
//		
//		Optional<LkpAvailablePincodes> lkpAvailablePincodes =lkpAvailablePincodesRepo.findByPinCode(CustomerDatailsDTO.getPinCode());
//		
//		String deliveryCode = deliveryPersonDetailsRepo.getdeliveryCode(CustomerDatailsDTO.getDistrictId(),lkpAvailablePincodes.get().getZoneId(),lkpAvailablePincodes.get().getDistanceId());
//		
//		if(refreshToken.isPresent()) {
//			CustomerDetails customerDetails = new CustomerDetails();
//			 int age = calculateAge(CustomerDatailsDTO.getdOB());
//			customerDetails.setName(CustomerDatailsDTO.getName());
//			customerDetails.setDateOfBirth(CustomerDatailsDTO.getdOB());
//		    customerDetails.setAge(age);
//			customerDetails.setAddressLine1(CustomerDatailsDTO.getAddressLine1());
//			customerDetails.setAddressLine2(CustomerDatailsDTO.getAddressLine2());
//			customerDetails.setGenderId(CustomerDatailsDTO.getGenderId());
//			customerDetails.setPhoneNumber(CustomerDatailsDTO.getPhoneNumber());
//			customerDetails.setMailId(CustomerDatailsDTO.getMailId());
//			customerDetails.setPinCode(CustomerDatailsDTO.getPinCode());
//			customerDetails.setDistrictId(CustomerDatailsDTO.getDistrictId());
//			customerDetails.setZoneId(lkpAvailablePincodes.get().getZoneId());
//			customerDetails.setDistanceId(lkpAvailablePincodes.get().getDistanceId());
//			customerDetails.setDeliveryCode(deliveryCode);
//			customerDetails.setStateId(CustomerDatailsDTO.getStateId());
//			customerDetails.setAddressType(CustomerDatailsDTO.getAddressType());
//			customerDetails.setDelivaryTimingId(CustomerDatailsDTO.getDelivaryTimingId());
//			customerDetails.setPragnent(CustomerDatailsDTO.isPragnent());;
//			customerDetails.setAlergic(CustomerDatailsDTO.isAllergic());
//			customerDetails.setCustomerStatusId(2);
//			customerDetails.setAlergicFruits(CustomerDatailsDTO.getFruitstoAvoid());
//			customerDetails.setLatitude(CustomerDatailsDTO.getLatitude());
//			customerDetails.setLongitude(CustomerDatailsDTO.getLongtitude());
//			customerDetails.setStatusId((long) 1);
//			customerDetails.setCreatedBy("User");
//			customerDetails.setCreatedTime(LocalDateTime.now());
//			// billing data
//			CheckoutData checkoutData = new CheckoutData();
//			
//			checkoutData.setFirstName(CustomerDatailsDTO.getB_lastName());
//		    checkoutData.setLastName(CustomerDatailsDTO.getB_lastName());
//		    checkoutData.setEmail(CustomerDatailsDTO.getMailId());
//		    checkoutData.setPhone(CustomerDatailsDTO.getPhoneNumber());
//		    checkoutData.setPincode(CustomerDatailsDTO.getPinCode());
//		    checkoutData.setCreatedAt(LocalDateTime.now());
//		    // Billing Address
//		    checkoutData.setB_firstName(CustomerDatailsDTO.getB_firstName());
//		    checkoutData.setB_lastName(CustomerDatailsDTO.getB_lastName());
//		    checkoutData.setB_email(CustomerDatailsDTO.getB_email());
//		    checkoutData.setB_phone(CustomerDatailsDTO.getB_phone());
//		    checkoutData.setB_address(CustomerDatailsDTO.getB_address());
//		    checkoutData.setB_companyName(CustomerDatailsDTO.getB_companyName());
//		    checkoutData.setB_gstNumber(CustomerDatailsDTO.getB_gstNumber());
//		    checkoutData.setB_landmark(CustomerDatailsDTO.getB_landmark());
//		    checkoutData.setB_city(CustomerDatailsDTO.getB_city());
//		    checkoutData.setB_state(CustomerDatailsDTO.getB_state());
//		    
//		    checkoutDataRepo.save(checkoutData);
//			
//			
//			CustomerDetails saved = customerDetailsRepo.save(customerDetails);
//			
//			 return new CustomerResponseDto(saved.getId(), saved.getName());
//			
//		}
//		else {
//			return null;
//		}
//	}

//	public List<CustomerResponseDto> SaveCustomerDetails(List<CustomerDatailsDTO> customerList) throws IOException {
//		List<CustomerResponseDto> responseList = new ArrayList<>();
//
//		for (CustomerDatailsDTO dto : customerList) {
//
//			Optional<RefreshToken> refreshToken = refreshTokenRepo
//					.findTopByPhoneNumberOrderByCreatedTimeDesc(dto.getPhoneNumber());
//			Optional<LkpAvailablePincodes> lkpAvailablePincodes = lkpAvailablePincodesRepo
//					.findByPinCode(dto.getPinCode());
//			System.out.println(dto.getPhoneNumber());
//			//System.out.println(refreshToken.get().getResfreshToken());
//
//			if (refreshToken.isPresent() && lkpAvailablePincodes.isPresent()) {
//				String deliveryCode = deliveryPersonDetailsRepo.getdeliveryCode(dto.getDistrictId(),
//						lkpAvailablePincodes.get().getZoneId(), lkpAvailablePincodes.get().getDistanceId());
//				System.out.println("in");
//				CustomerDetails customerDetails = new CustomerDetails();
//				int age = calculateAge(dto.getdOB());
//
//				// Set customer details
//				customerDetails.setName(dto.getName());
//				customerDetails.setDateOfBirth(dto.getdOB());
//				customerDetails.setAge(age);
//				customerDetails.setAddressLine1(dto.getAddressLine1());
//				customerDetails.setAddressLine2(dto.getAddressLine2());
//				customerDetails.setGenderId(dto.getGenderId());
//				customerDetails.setPhoneNumber(dto.getPhoneNumber());
//				customerDetails.setMailId(dto.getMailId());
//				customerDetails.setPinCode(dto.getPinCode());
//				customerDetails.setDistrictId(dto.getDistrictId());
//				customerDetails.setZoneId(lkpAvailablePincodes.get().getZoneId());
//				customerDetails.setDistanceId(lkpAvailablePincodes.get().getDistanceId());
//				customerDetails.setDeliveryCode(deliveryCode);
//				customerDetails.setStateId(dto.getStateId());
//				customerDetails.setAddressType(dto.getAddressType());
//				customerDetails.setDelivaryTimingId(dto.getDelivaryTimingId());
//				customerDetails.setPragnent(dto.isPragnent());
//				customerDetails.setAlergic(dto.isAllergic());
//				customerDetails.setCustomerStatusId(2);
//				customerDetails.setAlergicFruits(dto.getFruitstoAvoid());
//				customerDetails.setEggPreferd(dto.getIsEggPreferd());
//				customerDetails.setLatitude(dto.getLatitude());
//				customerDetails.setLongitude(dto.getLongtitude());
//				customerDetails.setStatusId(1L);
//				customerDetails.setCreatedBy("User");
//				customerDetails.setCreatedTime(LocalDateTime.now());
//
//				// Billing info
//				CheckoutData checkoutData = new CheckoutData();
//				checkoutData.setFirstName(dto.getB_firstName());
//				checkoutData.setLastName(dto.getB_lastName());
//				checkoutData.setEmail(dto.getB_email());
//				checkoutData.setPhone(dto.getB_phone());
//				checkoutData.setPincode(dto.getPinCode());
//				checkoutData.setCreatedAt(LocalDateTime.now());
//
//				// Billing address
//				checkoutData.setB_firstName(dto.getB_firstName());
//				checkoutData.setB_lastName(dto.getB_lastName());
//				checkoutData.setB_email(dto.getB_email());
//				checkoutData.setB_phone(dto.getB_phone());
//				checkoutData.setB_address(dto.getB_address());
//				checkoutData.setB_companyName(dto.getB_companyName());
//				checkoutData.setB_gstNumber(dto.getB_gstNumber());
//				checkoutData.setB_landmark(dto.getB_landmark());
//				checkoutData.setB_city(dto.getB_city());
//				checkoutData.setB_state(dto.getB_state());
//
//				checkoutDataRepo.save(checkoutData);
//				CustomerDetails saved = customerDetailsRepo.save(customerDetails);
//
//				// Add to response list
//				responseList.add(new CustomerResponseDto(saved.getId(), saved.getName()));
//				System.out.println(responseList);
//			}
//			if (!refreshToken.isPresent()) {
//				
//			}
//		}
//
//		return responseList;
//	}

	public int calculateAge(Date birthDate) {

		LocalDate dob = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if ((dob != null)) {
			return Period.between(dob, LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}

//	public boolean savePackDetails(List<packageDetailsSaveRequest> request) {
//		
//		for (packageDetailsSaveRequest dto : request) {
//		boolean isCustomerIdIsPresent = customerDetailsRepo.existsById(dto.getCustomerId());
//		if (isCustomerIdIsPresent) {
//			CustomerDetails customerDetails = new CustomerDetails();
//
//			
//			customerDetails.setPackDetailsId(dto.getPackDetailsId());
//			customerDetails.setId(dto.getCustomerId());
//			customerDetails.setCustomerStatusId(3);
//			customerDetailsRepo.updatePackDetails( customerDetails.getPackDetailsId(),
//					customerDetails.getId(), customerDetails.getCustomerStatusId());
//			return true;
//		}
//		return false;
//		}
//	}
	
	public List<CustomerResponseDto> SaveCustomerDetails(List<CustomerDatailsDTO> customerList) throws IOException {
	    List<CustomerResponseDto> responseList = new ArrayList<>();

	    for (CustomerDatailsDTO dto : customerList) {

	        Optional<RefreshToken> refreshToken = refreshTokenRepo
	                .findTopByPhoneNumberOrderByCreatedTimeDesc(dto.getPhoneNumber());

	        Optional<LkpAvailablePincodes> lkpAvailablePincodes = lkpAvailablePincodesRepo
	                .findByPinCode(dto.getPinCode());

	        // ❌ If refresh token not found → return 400 Bad Request
	        if (refreshToken.isEmpty()) {
	            throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "The phone number used does not match the OTP verified number"
	            );
	        }

	        // ❌ If pincode not available → return 400 Bad Request
	        if (lkpAvailablePincodes.isEmpty()) {
	            throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "Delivery not available for this pincode"
	            );
	        }

	        // ===================== Your existing code ======================
	        String deliveryCode = deliveryPersonDetailsRepo.getdeliveryCode(
	                dto.getDistrictId(),
	                lkpAvailablePincodes.get().getZoneId(),
	                lkpAvailablePincodes.get().getDistanceId()
	        );

	        CustomerDetails customerDetails = new CustomerDetails();
	        int age = calculateAge(dto.getdOB());

	        customerDetails.setName(dto.getName());
	        customerDetails.setDateOfBirth(dto.getdOB());
	        customerDetails.setAge(age);
	        customerDetails.setAddressLine1(dto.getAddressLine1());
	        customerDetails.setAddressLine2(dto.getAddressLine2());
	        customerDetails.setGenderId(dto.getGenderId());
	        customerDetails.setPhoneNumber(dto.getPhoneNumber());
	        customerDetails.setMailId(dto.getMailId());
	        customerDetails.setPinCode(dto.getPinCode());
	        customerDetails.setDistrictId(dto.getDistrictId());
	        customerDetails.setZoneId(lkpAvailablePincodes.get().getZoneId());
	        customerDetails.setDistanceId(lkpAvailablePincodes.get().getDistanceId());
	        customerDetails.setDeliveryCode(deliveryCode);
	        customerDetails.setStateId(dto.getStateId());
	        customerDetails.setAddressType(dto.getAddressType());
	        customerDetails.setDelivaryTimingId(dto.getDelivaryTimingId());
	        customerDetails.setPragnent(dto.isPragnent());
	        customerDetails.setAlergic(dto.isAllergic());
	        customerDetails.setIsDiabetic(dto.getIsDiabetic()); 
	        customerDetails.setCustomerStatusId(2);
	        customerDetails.setAlergicFruits(dto.getFruitstoAvoid());
	        customerDetails.setEggPreferd(dto.getIsEggPreferd());
	        customerDetails.setLatitude(dto.getLatitude());
	        customerDetails.setLongitude(dto.getLongtitude());
	        customerDetails.setStatusId(1L);
	        customerDetails.setCreatedBy("User");
	        customerDetails.setCreatedTime(LocalDateTime.now());

	        CheckoutData checkoutData = new CheckoutData();
	        checkoutData.setFirstName(dto.getB_firstName());
	        checkoutData.setLastName(dto.getB_lastName());
	        checkoutData.setEmail(dto.getB_email());
	        checkoutData.setPhone(dto.getB_phone());
	        checkoutData.setPincode(dto.getPinCode());
	        checkoutData.setCreatedAt(LocalDateTime.now());

	        checkoutData.setB_firstName(dto.getB_firstName());
	        checkoutData.setB_lastName(dto.getB_lastName());
	        checkoutData.setB_email(dto.getB_email());
	        checkoutData.setB_phone(dto.getB_phone());
	        checkoutData.setB_address(dto.getB_address());
	        checkoutData.setB_companyName(dto.getB_companyName());
	        checkoutData.setB_gstNumber(dto.getB_gstNumber());
	        checkoutData.setB_landmark(dto.getB_landmark());
	        checkoutData.setB_city(dto.getB_city());
	        checkoutData.setB_state(dto.getB_state());

	        checkoutDataRepo.save(checkoutData);
	        CustomerDetails saved = customerDetailsRepo.save(customerDetails);

	        responseList.add(new CustomerResponseDto(saved.getId(), saved.getName()));
	    }

	    return responseList;
	}


	public List<Map<String, Object>> savePackDetails(List<packageDetailsSaveRequest> requestList) {
	    List<Map<String, Object>> results = new ArrayList<>();

	    for (packageDetailsSaveRequest dto : requestList) {
	        Map<String, Object> result = new HashMap<>();
	        result.put("customerId", dto.getCustomerId());

	        boolean isCustomerIdPresent = customerDetailsRepo.existsById(dto.getCustomerId());

	        if (isCustomerIdPresent) {

	            customerDetailsRepo.updatePackDetails(
	            		dto.getPackDetailsId(),
	                dto.getCustomerId(),
	                3 // customer status
	            );
	            result.put("status", "Updated Successfully");
	        } else {
	            result.put("status", "Customer ID Not Found");
	        }

	        results.add(result);
	    }

	    return results;
	}

	
	public String UpdateAddressDetails(addressChangeDto request) {
		

		    CustomerDetails customer = customerDetailsRepo.findById(request.getCustomerId())
		            .orElseThrow(() -> new RuntimeException("Customer not found"));

		    customer.setNewAddressLine1(request.getAddressLine1());
		    customer.setNewAddressLine2(request.getAddressLine2());
		    customer.setNewAddressType(request.getAddressType());
		    customer.setNewDistrictId(request.getDistrictId());
		    customer.setNewStateId(request.getStateId());
		    customer.setNewPinCode(request.getPinCode());
		    customer.setNewDelivaryTimingId(request.getDelivaryTimingId());
		    customer.setNewLatitude(request.getNewLatitude());
		    customer.setNewLongitude(request.getNewLongitude());

		    customer.setAddressPending(true);
		    customer.setAddressUpdateApplyDate(LocalDate.now().plusDays(2));
		    customer.setId(request.getCustomerId());

		    customerDetailsRepo.save(customer);

		    return "Address update requested — will take effect after 2 days.";
		}

	public boolean UpdateLocationAddress(addressChangeDto request) { 
		//LocalDate applyDate = LocalDate.now().plusDays(2);
		
		 Optional<LkpAvailablePincodes> lkpAvailablePincodes = lkpAvailablePincodesRepo
	                .findByPinCode(request.getPinCode());

	   

	        // ❌ If pincode not available → return 400 Bad Request
	        if (lkpAvailablePincodes.isEmpty()) {
	            throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "Delivery not available for this pincode"
	            );
	        }

	        // ===================== Your existing code ======================
	        String deliveryCode = deliveryPersonDetailsRepo.getdeliveryCode(
	        		request.getDistrictId(),
	                lkpAvailablePincodes.get().getZoneId(),
	                lkpAvailablePincodes.get().getDistanceId()
	        );
	        CustomerDetails customerDetails = new CustomerDetails();
	        customerDetails.setZoneId(lkpAvailablePincodes.get().getZoneId());
	        customerDetails.setDistanceId(lkpAvailablePincodes.get().getDistanceId());
	        customerDetails.setDeliveryCode(deliveryCode);
	        customerDetails.setId(request.getCustomerId());
	        
	        CustomerDetails saved = customerDetailsRepo.save(customerDetails);
		int rowsAffected = customerDetailsRepo.UpdateLocationAddress(request.getCustomerId(), request.getAddressLine1(),
				request.getAddressLine2(), request.getAddressType(), request.getDistrictId(), request.getStateId(),
				request.getPinCode(), request.getDelivaryTimingId(),request.getNewLatitude(),request.getNewLongitude());
		return rowsAffected > 0;
	}

//	public boolean checkCustomaizationEnable(int customerId) {
//		LocalDateTime paymentTime = customerDetailsRepo.checkCustomaizationEnable(customerId);
//
//		boolean isEligible = paymentTime != null && Duration.between(paymentTime, LocalDateTime.now()).toHours() >= 24;
//
//		return isEligible;
//	}
	
	public boolean checkCustomaizationEnable(int customerId) {

	    LocalDateTime paymentTime = customerDetailsRepo.checkCustomaizationEnable(customerId);


	    // Rule 0: If no payment, no customization
	    if (paymentTime == null) {
	        return false;
	    }
System.out.println(paymentTime);

	    LocalDateTime now = LocalDateTime.now();

	    //  Rule 1: 24 Hours must be completed
	    if (Duration.between(paymentTime, now).toHours() < 24) {
	        return false;
	    }

	    //  Rule 2: Only allow between 9:30 AM  7:30 PM
	    LocalTime startTime = LocalTime.of(9, 30);
	    LocalTime endTime   = LocalTime.of(23, 30);

	    LocalTime currentTime = now.toLocalTime();
System.out.println(currentTime );

	    if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime)) {
	        return false;
	    }

	    //  Rule 3: Block Saturday completely
	    DayOfWeek today = now.getDayOfWeek();
	//    if (today == DayOfWeek.SATURDAY) {
	 //       return false;
	  //  }

	    //  Rule 4: Block Sunday completely (No delivery)
//	    if (today == DayOfWeek.SUNDAY) {
//	        return false;
//	    }

	    //  If all conditions pass  Customization ENABLED
	    return true;
	}


	public CustomerwebsiteresDto saveCheckoutCustomerDetails(CustomerSaverRequestDto data) {

		CustomerwebsiteresDto responseDto = new CustomerwebsiteresDto();

		CheckoutData checkoutData = new CheckoutData();
		checkoutData.setFirstName(data.getFirstName());
		checkoutData.setFirstName(data.getFirstName());
		checkoutData.setLastName(data.getLastName());
		checkoutData.setEmail(data.getEmail());
		checkoutData.setPhone(data.getPhone());
		checkoutData.setPincode(data.getPincode());
		checkoutData.setAddress(data.getAddress());
		checkoutData.setLocality(data.getLocality());
		checkoutData.setLandmark(data.getLandmark());
		checkoutData.setCity(data.getCity());
		checkoutData.setState(data.getState());
		checkoutData.setPlanName(data.getPlanName());
		checkoutData.setIsEggadded(data.getIsEggadded());
		checkoutData.setDeliveryNotes(data.getDeliveryNotes());
		checkoutData.setCreatedAt(LocalDateTime.now());
		// Billing Address
		checkoutData.setB_firstName(data.getB_firstName());
		checkoutData.setB_lastName(data.getB_lastName());
		checkoutData.setB_email(data.getB_email());
		checkoutData.setB_phone(data.getB_phone());
		checkoutData.setB_address(data.getB_address());
		checkoutData.setB_companyName(data.getB_companyName());
		checkoutData.setB_gstNumber(data.getB_gstNumber());
		checkoutData.setB_landmark(data.getB_landmark());
		checkoutData.setB_city(data.getB_city());
		checkoutData.setB_state(data.getB_state());

		checkoutDataRepo.save(checkoutData);

		String accessToken = jwtUtil.generateAccessToken(data.getPhone());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(data.getPhone());

		if (refreshToken != null) {
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setName(data.firstName);
//			customerDetails.setDateOfBirth(CustomerDatailsDTO.getdOB());
//		    customerDetails.setAge(age);
			customerDetails.setAddressLine1(data.getAddress() + "," + data.getLocality() + "," + data.getLandmark());
			customerDetails.setAddressLine2(data.getCity() + "," + data.getState());
			customerDetails.setPhoneNumber(data.getPhone());
			customerDetails.setMailId(data.getEmail());
			customerDetails.setPinCode(data.getPincode());
			customerDetails.setEggPreferd(data.getIsEggadded());
			customerDetails.setDistrictId(1);
			customerDetails.setZoneId(1);
			customerDetails.setDistanceId(1);
			customerDetails.setDeliveryCode("001");
			customerDetails.setStateId(1);
			customerDetails.setAddressType("home");
			customerDetails.setDelivaryTimingId(1);
			customerDetails.setPragnent(false);
			customerDetails.setAlergic(false);
			customerDetails.setCustomerStatusId(3);
			customerDetails.setAlergicFruits(null);
			customerDetails.setLatitude(null);
			customerDetails.setLongitude(null);
			customerDetails.setStatusId((long) 1);
			customerDetails.setCreatedBy("website");
			customerDetails.setCreatedTime(LocalDateTime.now());
			System.out.println(data.getPlanName());
			if ("Premium".equals(data.getPlanName())) {
				customerDetails.setPackDetailsId(3);
			} else if ("Standard".equals(data.getPlanName())) {
				customerDetails.setPackDetailsId(2);
			} else if ("Kids".equals(data.getPlanName())) {
				customerDetails.setPackDetailsId(1);
			}

			CustomerDetails saved = customerDetailsRepo.save(customerDetails);

			// return new CustomerResponseDto(saved.getId(), saved.getName());

			responseDto.setId(saved.getId());
			responseDto.setName(saved.getName());
			responseDto.setAccessToken(accessToken);
			responseDto.setRefreshToken(refreshToken.getResfreshToken());

			return responseDto;

		}
		return null;

	}

	public String deleteCustomerDetails(long customerId) {

	    Optional<CustomerDetails> customerOpt = customerDetailsRepo.findById(customerId);

	    if (customerOpt.isEmpty()) {
	        return "Customer not found";
	    }

	    CustomerDetails customer = customerOpt.get();

	    // 🔥 Condition: must cancel subscription before deletion
	    if (!customer.getIsCancelled()) {   
	        return "First cancel subscription before deleting the account.";
	    }

	    // Further safety: ensure scheduled cancellation date is reached
	    if (customer.getCancelScheduledDate() != null &&
	       customer.getCancelScheduledDate().isAfter(LocalDate.now())) {
	        return "Subscription cancellation in process. You can delete account after " 
	                + customer.getCancelScheduledDate();
	    }

	    // If subscription cancelled, allow delete/update status
	    int result = customerDetailsRepo.updateCustomerStatus(customerId);

	    return result > 0 
	            ? "Customer deleted successfully." 
	            : "Failed to delete customer.";
	}

}
