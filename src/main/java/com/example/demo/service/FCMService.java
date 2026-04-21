package com.example.demo.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FCMService {

    /**
     * Send notification to a single device token.
     */
    public void sendNotification(String token, String title, String body) {
        if (token == null || token.isBlank()) {
            System.out.println("⚠️ Skipping — empty device token.");
            return;
        }

        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    // Android: High priority so it wakes the device
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .setNotification(AndroidNotification.builder()
                                    .setSound("default")
                                    .build())
                            .build())
                    // iOS: Sound + badge
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder()
                                    .setSound("default")
                                    .setBadge(1)
                                    .build())
                            .build())
                    .build();

            String messageId = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ FCM sent | token=" + token.substring(0, 10) + "... | id=" + messageId);

        } catch (FirebaseMessagingException e) {
            System.err.println("❌ FCM failed | token=" + token.substring(0, 10) + "... | error=" + e.getMessage());
        }
    }

    /**
     * Send same notification to a list of tokens in one batch call.
     * FCM batch limit = 500 per call.
     */
    public void sendBatch(List<String> tokens, String title, String body) {
        if (tokens == null || tokens.isEmpty()) return;

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setNotification(AndroidNotification.builder()
                                .setSound("default")
                                .build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setSound("default")
                                .setBadge(1)
                                .build())
                        .build())
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            System.out.println("📊 Batch FCM | success=" + response.getSuccessCount()
                    + " | failed=" + response.getFailureCount()
                    + " | total=" + tokens.size());
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ Batch FCM error: " + e.getMessage());
        }
    }
}