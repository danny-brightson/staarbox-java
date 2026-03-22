package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.RefreshToken;
import com.example.demo.repo.RefreshTokenRepo;
//import com.google.api.client.auth.oauth2.TokenRequest;

@RestController
@RequestMapping("/api/token")
public class TokenController {
//
//    @Autowired
//    private RefreshTokenRepo refreshTokenRepo;
//
//    @PostMapping("/save")
//    public ResponseEntity<String> saveToken(@RequestBody TokenRequest request) {
//        RefreshToken token = refreshTokenRepo.findByPhoneNumber(request.getPhoneNumber())
//            .orElse(new RefreshToken());
//
//        token.setPhoneNumber(request.getPhoneNumber());
//        token.setResfreshToken(request.getFcmToken()); // save FCM token here
//        token.setIsTokenValid(true);
//        refreshTokenRepo.save(token);
//
//        return ResponseEntity.ok("Token saved");
//    }
}

