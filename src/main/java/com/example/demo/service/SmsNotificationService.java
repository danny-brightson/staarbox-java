package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsNotificationService {

    @Autowired
    private UserRepository userRepo;

    private static final int MAX_SMS_PER_DAY = 3;

    private static final String AUTH_KEY = "YOUR_AUTH_KEY";
    private static final String FLOW_ID = "YOUR_FLOW_ID";

    // ==============================
    // MAIN METHOD
    // ==============================
    public void sendSmartSmsToAllUsers(String message, String type) {

        List<User> users = userRepo.findAll();

        for (User user : users) {

            if (!canSendSms(user, type)) {
                continue;
            }

            sendSms(user.getMobile(), message);
            updateSmsTracking(user);
        }
    }

    // ==============================
    // SEND SMS (MSG91)
    // ==============================
    public void sendSms(String mobile, String message) {

        String url = "https://control.msg91.com/api/v5/flow";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authkey", AUTH_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("flow_id", FLOW_ID);
        body.put("mobiles", "91" + mobile);
        body.put("MESSAGE", message);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {
            System.out.println("📩 Sending SMS to: " + mobile);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            System.out.println("✅ SMS SENT: " + mobile + " | " + response.getBody());

        } catch (Exception e) {
            System.out.println("❌ SMS FAILED: " + mobile);
            e.printStackTrace();
        }
    }

    // ==============================
    // SMART FILTER LOGIC
    // ==============================
    public boolean canSendSms(User user, String type) {
        // Null safe
        if (user.getSmsCountToday() == null) {
            user.setSmsCountToday(0);
        }
        
        if (!user.isSmsEnabled()) {
            return false;
        }

        if ("RENEWAL".equals(type) || "LAST_REMINDER".equals(type)) {
            return true;
        }

        if (user.getSmsCountToday() >= MAX_SMS_PER_DAY) {
            return false;
        }

        if (user.getLastSmsSent() != null &&
                user.getLastSmsSent().isAfter(LocalDateTime.now().minusHours(2))) {
            return false;
        }

        return true;
    }

    // ==============================
    // TRACKING UPDATE
    // ==============================
    public void updateSmsTracking(User user) {

        user.setSmsCountToday(user.getSmsCountToday() + 1);
        user.setLastSmsSent(LocalDateTime.now());

        userRepo.save(user);
    }

    // ==============================
    // DAILY RESET (Scheduler)
    // ==============================
    public void resetDailySmsCount() {

        List<User> users = userRepo.findAll();

        for (User user : users) {
            user.setSmsCountToday(0);
            userRepo.save(user);
        }

        System.out.println("🔄 SMS count reset for all users");
    }
}