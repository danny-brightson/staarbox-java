package com.example.demo.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HealthUpdateRequestDto;
import com.example.demo.service.HealthService;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @PutMapping("/update")
    public ResponseEntity<String> updateHealth(
            @RequestBody HealthUpdateRequestDto request) {

        String response = healthService.updateHealthDetails(request);
        return ResponseEntity.ok(response);
    }
}