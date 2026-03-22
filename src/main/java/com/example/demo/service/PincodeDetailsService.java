

package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AvailablePromoCode;
import com.example.demo.entity.PincodeDetails;
import com.example.demo.repo.AvailablePromoCodeRepo;
import com.example.demo.repo.LkpAvailableDistrictRepo;
import com.example.demo.repo.LkpAvailablePincodesRepo;
import com.example.demo.repo.PincodeDetailsRepo;

@Service
public class PincodeDetailsService {

	@Autowired
	private PincodeDetailsRepo pincodeDetailsRepo;

	@Autowired
	private AvailablePromoCodeRepo availablePromoCodeRepo;
	
	@Autowired
	private LkpAvailableDistrictRepo lkpAvailableDistrictRepo;
	
	@Autowired
	private  LkpAvailablePincodesRepo lkpAvailablePincodesRepo;


	public boolean CheckPinCodeAvailability(String pincode) throws IOException {
		
		String District= lkpAvailablePincodesRepo.checkPincode(pincode);
		//PincodeAvailableResponseDto availableresponse = new PincodeAvailableResponseDto();
		PincodeDetails pincodeDetails = new PincodeDetails();
		
		  if (District !=null) {
		        return true;
		    }
		 
	else {


				pincodeDetails.setPinCode(pincode);
				//pincodeDetails.setDistrict();
				pincodeDetails.setStatusId((long) 1);
				pincodeDetails.setCreatedBy("User");
				pincodeDetails.setCreatedTime(LocalDateTime.now());
				pincodeDetailsRepo.save(pincodeDetails);
				return false;
			}

	}


//	public boolean CheckPromoCodeAvailability(String promoCode) {
//	
//		Boolean isvalid = availablePromoCodeRepo.checkStatusofPromoCode(promoCode);
//		
//		if (Boolean.TRUE.equals(isvalid)) {
//			return true;
//		}
//		else {
//			return false;
//		}
//		
//	}
	
	public Map<String, Object> checkPromoCodeAvailability(String promoCode) {
	    Optional<AvailablePromoCode> promo = availablePromoCodeRepo.findValidPromo(promoCode);
	    Map<String, Object> result = new HashMap<>();
	    if (promo.isPresent() && Boolean.TRUE.equals(promo.get().getIsValid())) {
	        result.put("valid", true);
	        result.put("discountPercentage", promo.get().getDiscountPercentage());
	    } else {
	        result.put("valid", false);
	        result.put("discountPercentage", 0);
	    }
	    System.out.println(result);
	    return result;
	}


//		PincodeAvailableResponseDto availableresponse = new PincodeAvailableResponseDto();
//		PincodeDetails pincodeDetails = new PincodeDetails();
//
//		String apiUrl = "https://api.postalpincode.in/pincode/" + pincode;
//		URL url = new URL(apiUrl);
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("GET");
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//		String inputLine;
//		StringBuilder response = new StringBuilder();
//		
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();
//		
//		ObjectMapper mapper = new ObjectMapper();
//
//		// Parse response into a List of PincodeResponse
//		List<PincodeResponseDto> pincodeDistrictList = Arrays
//				.asList(mapper.readValue(response.toString(), PincodeResponseDto[].class));
//		
//		if(pincodeDistrictList.isEmpty()) {
//			availableresponse.setAvailable(false);
//			availableresponse.setDistrict("this PinCode is not Available");
//			return availableresponse;
//		}
//
//		if (!pincodeDistrictList.isEmpty() && "Success".equals(pincodeDistrictList.get(0).Status)) {
//			System.out.println(pincodeDistrictList.get(0).PostOffice.get(0).District);
//		}
//
//		List<LkpAvailableDistrict> districtList = lkpAvailableDistrictRepo.findAll();
//
//		for (LkpAvailableDistrict district : districtList) {
//		    String dbDistrict = district.getDistrict().trim();
//		    String pincodeDistrict = pincodeDistrictList.get(0).getPostOffice().get(0).getDistrict().trim();
//		    
//		    System.out.println(dbDistrict);
//		    
//		    System.out.println(pincodeDistrict);
//
//		    if (dbDistrict.equalsIgnoreCase(pincodeDistrict)) {
//		        availableresponse.setAvailable(true);
//		        availableresponse.setDistrict(pincodeDistrict);
//		        break;
//		    }
//		} 
//			if(availableresponse.isAvailable != true) {
//
//				availableresponse.setAvailable(false);
//				availableresponse.setDistrict(pincodeDistrictList.get(0).PostOffice.get(0).District);
//
//				pincodeDetails.setPinCode(pincode);
//				pincodeDetails.setDistrict(pincodeDistrictList.get(0).PostOffice.get(0).District);
//				pincodeDetails.setStatusId((long) 1);
//				pincodeDetails.setCreatedBy("User");
//				pincodeDetails.setCreatedTime(LocalDateTime.now());
//				pincodeDetailsRepo.save(pincodeDetails);
//
//			}
//
//		return availableresponse;

//	}
}
