package com.example.demo.service;

import com.example.demo.entity.UserDevice;
import com.example.demo.repo.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private FCMService fcmService;

    /**
     * Send a notification to every registered device.
     * Uses FCM batch for efficiency (one API call instead of N calls).
     */
    public void sendToAllUsers(String title, String message) {
        List<UserDevice> devices = userDeviceRepository.findAll();

        if (devices.isEmpty()) {
            System.out.println("⚠️ No registered devices found.");
            return;
        }

        List<String> tokens = devices.stream()
                .map(UserDevice::getDeviceToken)
                .filter(t -> t != null && !t.isBlank())
                .collect(Collectors.toList());

        System.out.println("📤 Sending [" + title + "] to " + tokens.size() + " devices...");
        fcmService.sendBatch(tokens, title, message);
    }

    /**
     * Send a notification to a specific user's devices only.
     */
    public void sendToUser(Long userId, String title, String message) {
        List<UserDevice> devices = userDeviceRepository.findByUserId(userId);

        if (devices.isEmpty()) {
            System.out.println("⚠️ No devices found for userId=" + userId);
            return;
        }

        for (UserDevice device : devices) {
            fcmService.sendNotification(device.getDeviceToken(), title, message);
        }
    }

    /**
     * Register a new device token for a user.
     * Call this from your mobile app after user logs in.
     */
    public UserDevice registerDevice(Long userId, String deviceToken) {
        // Avoid duplicate tokens
        List<UserDevice> existing = userDeviceRepository.findByUserId(userId);
        boolean alreadyExists = existing.stream()
                .anyMatch(d -> deviceToken.equals(d.getDeviceToken()));

        if (alreadyExists) {
            System.out.println("ℹ️ Token already registered for userId=" + userId);
            return existing.get(0);
        }

        UserDevice device = new UserDevice(userId, deviceToken);
        UserDevice saved = userDeviceRepository.save(device);
        System.out.println("✅ Device registered | userId=" + userId);
        return saved;
    }
}