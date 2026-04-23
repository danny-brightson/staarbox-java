package com.example.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserDevice;
import com.example.demo.service.NotificationService;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDevice(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String deviceToken = body.get("deviceToken").toString();

        if (deviceToken == null || deviceToken.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "deviceToken is required"
            ));
        }

        UserDevice saved = notificationService.registerDevice(userId, deviceToken);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "message", "Device registered successfully",
            "deviceId", saved.getId()
        ));
    }

    @PostMapping("/notify/{userId}")
    public ResponseEntity<?> notifyUser(@PathVariable Long userId,
                                        @RequestBody Map<String, String> body) {
        notificationService.sendToUser(userId,
            body.getOrDefault("title", "StarBox"),
            body.getOrDefault("message", ""));
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/notify-all")
    public ResponseEntity<?> notifyAll(@RequestBody Map<String, String> body) {
        notificationService.sendToAllUsers(
            body.getOrDefault("title", "StarBox"),
            body.getOrDefault("message", ""));
        return ResponseEntity.ok(Map.of("success", true));
    }
}