package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SandwichResponseDTO;
import com.example.demo.service.SandwichService;

@RestController
@RequestMapping("/api/sandwich")
public class SandwichController {

    @Autowired
    private SandwichService sandwichService;

    @GetMapping("/{customerId}")
    public ResponseEntity<SandwichResponseDTO> 
            getSandwichDetails(@PathVariable Long customerId) {

        SandwichResponseDTO response =
                sandwichService.getSandwichDetails(customerId);

        return ResponseEntity.ok(response);
    }
}
