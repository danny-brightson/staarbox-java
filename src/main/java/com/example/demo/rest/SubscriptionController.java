package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UpdatePackRequest;
import com.example.demo.dto.RenewalResponse;
import com.example.demo.service.SubscriptionService;

@RestController
@RequestMapping("/api")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/update-pack")
    public String updatePack(@RequestBody UpdatePackRequest request) {
        return subscriptionService.updatePackDetails(
                request.getCustomerId(),
                request.getPackId()
        );
    }

    @GetMapping("/customer-renewal-details")
    public RenewalResponse getRenewalDetails(@RequestParam Long customerId) {
        return subscriptionService.getRenewalDetails(customerId);
    }
}