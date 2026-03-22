
package com.example.demo.service;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SandwichDTO;
import com.example.demo.dto.SandwichResponseDTO;
import com.example.demo.projection.CustomerPackDistrictProjection;
import com.example.demo.repo.AvailableSandwichesRepository;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.PricePerPackDetailsRepository;

@Service
public class SandwichService {

    @Autowired
    private CustomerDetailsRepo customerRepo;

    @Autowired
    private PricePerPackDetailsRepository priceRepo;

    @Autowired
    private AvailableSandwichesRepository sandwichRepo;

    public SandwichResponseDTO getSandwichDetails(Long customerId) {

    	CustomerPackDistrictProjection result =
    	        customerRepo.findPackAndDistrictByCustomerId(customerId);

    	if (result == null) {
    	    throw new RuntimeException("Customer not found");
    	}

    	Integer packId = result.getPackDetailsId();
    	Integer districtId = result.getDistrictId();


        // Get Minimum Amount
        BigDecimal minAmount =
                priceRepo.findMinAmount(packId, districtId);

        if (minAmount == null) {
            minAmount = BigDecimal.ZERO;
        }

        List<SandwichDTO> sandwiches;

        // Case 3: Pack 4,5 → No sandwiches
        if (packId == 4 || packId == 5) {
            sandwiches = Collections.emptyList();
        }

        // Case 1: All sandwiches
        else if (List.of(1,2,3,6,7,8).contains(packId)) {
            sandwiches = sandwichRepo.findAllByDistrict(districtId);
        }

        // Case 2: Only Veg
        else {
            sandwiches = sandwichRepo
                    .findByDistrictAndCategory(districtId, "VEG");
        }

        return new SandwichResponseDTO(minAmount, sandwiches);
    }
}

