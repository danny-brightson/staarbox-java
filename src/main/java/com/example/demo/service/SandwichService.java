
package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	private static final int FRUIT_JAR_PACK_DETAILS_ID = 15;
	private static final int MADURAI_DISTRICT_ID = 1;

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

        BigDecimal minAmount =
                priceRepo.findMinAmount(packId, districtId);

        if (minAmount == null) {
            minAmount = BigDecimal.ZERO;
        }

        List<SandwichDTO> sandwiches = resolveSandwiches(packId, districtId);

        if (districtId == MADURAI_DISTRICT_ID) {
        	if (packId == FRUIT_JAR_PACK_DETAILS_ID) {
        		sandwiches = excludeJars(sandwiches, districtId);
        	} else {
        		sandwiches = includeJars(sandwiches, districtId);
        	}
        }

        return new SandwichResponseDTO(minAmount, sandwiches);
    }

    private List<SandwichDTO> resolveSandwiches(Integer packId, Integer districtId) {
        if (packId == 4 || packId == 5) {
            return new ArrayList<>();
        }
        if (List.of(1, 2, 3, 6, 7, 8).contains(packId)) {
            return new ArrayList<>(sandwichRepo.findAllByDistrict(districtId));
        }
        return new ArrayList<>(sandwichRepo.findByDistrictAndCategory(districtId, "VEG"));
    }

    private List<SandwichDTO> includeJars(List<SandwichDTO> sandwiches, Integer districtId) {
        Set<Long> existingIds = new HashSet<>();
        for (SandwichDTO item : sandwiches) {
        	if (item.getId() != null) {
        		existingIds.add(item.getId());
        	}
        }

        List<SandwichDTO> result = new ArrayList<>(sandwiches);
        for (SandwichDTO jar : sandwichRepo.findByDistrictAndCategory(districtId, "jar")) {
        	if (jar.getId() != null && !existingIds.contains(jar.getId())) {
        		result.add(jar);
        		existingIds.add(jar.getId());
        	}
        }

        result.sort(Comparator.comparing(
        		SandwichDTO::getSandwichName,
        		Comparator.nullsLast(String::compareToIgnoreCase)));
        return result;
    }

    private List<SandwichDTO> excludeJars(List<SandwichDTO> sandwiches, Integer districtId) {
        Set<Long> jarIds = new HashSet<>();
        for (SandwichDTO jar : sandwichRepo.findByDistrictAndCategory(districtId, "jar")) {
        	if (jar.getId() != null) {
        		jarIds.add(jar.getId());
        	}
        }

        return sandwiches.stream()
        		.filter(item -> item.getId() == null || !jarIds.contains(item.getId()))
        		.toList();
    }
}
