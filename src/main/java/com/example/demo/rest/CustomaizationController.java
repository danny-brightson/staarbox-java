package com.example.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomizationSummaryDto;
import com.example.demo.dto.CustomizedPackageRequest;
import com.example.demo.dto.PackageResponseDto;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.service.CustomaizationService;

@RestController
@RequestMapping("/api")
public class CustomaizationController {

	
    @Autowired
    private  CustomaizationService coustomaizationService;
    
    
    @GetMapping("/TodaysFruitsAndNutsList")
    public List<PackageResponseDto> CustomaizationList(@RequestParam long CustomerId) {
        return coustomaizationService.getPackageByCustomerId(CustomerId);
    }
    
    @PostMapping("/saveCustomaizationDetails")
    public boolean saveCustomaizationDetails(@RequestBody CustomizedPackageRequest request) {
        return coustomaizationService.mapToEntity(request);
    }

    @GetMapping("/tomorrow-summary")
    public CustomizationSummaryDto getTomorrowSummary() {
        return coustomaizationService.getTomorrowSummary();
    }
	
}
