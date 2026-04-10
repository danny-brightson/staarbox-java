package com.example.demo.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.VersionRequest;
import com.example.demo.dto.VersionResponse;
import com.example.demo.service.VersionCheckService;

@RestController
@RequestMapping("/api/version")
public class VersionCheckController {

    @Autowired
    private VersionCheckService service;

    @PostMapping("/update")
    public ResponseEntity<VersionResponse> updateVersion(@RequestBody VersionRequest request) {
        VersionResponse response = service.updateVersion(request);
        return ResponseEntity.ok(response);
    }
}
