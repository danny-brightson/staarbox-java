package com.example.demo.rest;

import com.example.demo.entity.UserDevice;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST API for device token registration and manual notification triggers.
 *
 * POST /api/device/register              → Register mobile FCM token
 * POST /api/device/notify/{userId}       → Send notification to specific user
 * POST /api/device/notify-all            → Broadcast to all users
 */
@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Call this from your mobile app after login to save the FCM token.
     *
     * Request body:
     * {
     *   "userId": 1,
     *   "deviceToken": "fcm_token_from_mobile_app"
     * }
     */
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

    /**
     * Manually send a notification to a specific user.
     * Useful for testing or order-specific triggers.
     *
     * Request body:
     * {
     *   "title": "Your order is ready",
     *   "message": "Tap to track your delivery"
     * }
     */
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