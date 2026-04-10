package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HealthUpdateRequestDto;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.repo.CustomerDetailsRepo;



@Service
public class HealthService {

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;
    public String updateHealthDetails(HealthUpdateRequestDto request) {

        CustomerDetails customer = customerDetailsRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (request.getIsPregnant() != null) {
            customer.setPragnent(request.getIsPregnant());
        }

        if (request.getIsAllergic() != null) {
            customer.setAlergic(Boolean.TRUE.equals(request.getIsAllergic()));

            if (Boolean.TRUE.equals(request.getIsAllergic()) && request.getAllergyDetails() == null) {
                throw new RuntimeException("Allergy details required");
            }

            customer.setAlergicFruits(request.getAllergyDetails());
        }

        if (request.getIsDiabetic() != null) {
            customer.setIsDiabetic(request.getIsDiabetic());
        }

        customerDetailsRepo.save(customer);

        return "Health details updated successfully";
    }
}