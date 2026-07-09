package com.example.demo.service;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.UserDevice;
import com.example.demo.entity.UserNotificationPreference;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.UserDeviceRepository;
import com.example.demo.repo.UserNotificationPreferenceRepository;
import com.example.demo.util.MessageTemplates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    /** One push per phone number for these types, even with multiple active customer profiles. */
    private static final Set<String> ONCE_PER_PHONE_TYPES = Set.of(
            MessageTemplates.TYPE_FOOD_READY,
            MessageTemplates.TYPE_MORNING_DELIVERY,
            MessageTemplates.TYPE_BREAKFAST_TIP,
            MessageTemplates.TYPE_CUSTOMIZATION_OPEN,
            MessageTemplates.TYPE_CUSTOMIZATION_REMINDER,
            MessageTemplates.TYPE_LAST_REMINDER,
            MessageTemplates.TYPE_CUSTOMIZATION_CLOSED);

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private UserNotificationPreferenceRepository preferenceRepository;

    @Autowired
    private FCMService fcmService;

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    /**
     * Send notification to all registered active users.
     * Respects user preferences and only sends to active subscriptions.
     */
    @Transactional
    public void sendToAllUsers(String title, String message, String notificationType) {
        if (MessageTemplates.TYPE_DISPATCH.equalsIgnoreCase(notificationType)) {
            logger.info("Dispatch notifications are disabled | type={}", notificationType);
            return;
        }

        List<UserDevice> allDevices = userDeviceRepository.findAllActiveDevices();

        if (allDevices.isEmpty()) {
            logger.warn("No registered devices found");
            return;
        }

        List<UserDevice> eligibleDevices = selectEligibleDevices(allDevices, notificationType);

        if (eligibleDevices.isEmpty()) {
            logger.info("No eligible devices after filtering | type={}", notificationType);
            return;
        }

        logger.info("Sending notification to all users | type={} | eligibleDevices={} | totalDevices={}", 
                   notificationType, eligibleDevices.size(), allDevices.size());

        fcmService.sendBatch(eligibleDevices, title, message, notificationType);
    }

    /**
     * Send notification to a specific user's devices.
     */
    @Transactional
    public void sendToUser(Long userId, String title, String message, String notificationType) {
        if (MessageTemplates.TYPE_DISPATCH.equalsIgnoreCase(notificationType)) {
            logger.info("Dispatch notifications are disabled | userId={}", userId);
            return;
        }

        List<UserDevice> devices = userDeviceRepository.findByUserIdAndIsActiveTrue(userId);

        if (devices.isEmpty()) {
            logger.warn("No active devices found for userId={}", userId);
            return;
        }

        if (!shouldSendNotification(userId, notificationType)) {
            logger.debug("User has disabled this notification type | userId={} | type={}", 
                        userId, notificationType);
            return;
        }

        logger.info("Sending notification to user | userId={} | devices={} | type={}", 
                   userId, devices.size(), notificationType);

        for (UserDevice device : devices) {
            fcmService.sendNotification(device, title, message, notificationType);
        }
    }

    /** Minimum days between two resume-plan pushes for the same customer. */
    public static final int RENEWAL_REMINDER_MIN_GAP_DAYS = 3;

    /** Max resume-plan pushes per customer in a rolling week. */
    public static final int RENEWAL_REMINDER_MAX_PER_WEEK = 2;

    /** Rolling window for {@link #RENEWAL_REMINDER_MAX_PER_WEEK}. */
    public static final int RENEWAL_REMINDER_WINDOW_DAYS = 7;

    /**
     * Sends the resume-plan notification only to <strong>non-active</strong> subscribers:
     * {@code NextRenewalDate} is strictly before today (active = renewal date today or later).
     * Not sent via {@link #sendToAllUsers} and never uses {@link #hasActiveSubscription}.
     * Rate limit: up to {@link #RENEWAL_REMINDER_MAX_PER_WEEK} per {@link #RENEWAL_REMINDER_WINDOW_DAYS} days,
     * with at least {@link #RENEWAL_REMINDER_MIN_GAP_DAYS} between sends (any {@code notification_log} row counts).
     */
    @Transactional
    public void sendLapsedSubscriptionRenewalReminders(String title, String message, String notificationType) {
        if (!MessageTemplates.TYPE_RENEWAL_REMINDER.equalsIgnoreCase(notificationType)) {
            logger.warn("sendLapsedSubscriptionRenewalReminders expects type={} but got={}",
                    MessageTemplates.TYPE_RENEWAL_REMINDER, notificationType);
        }

        List<Long> userIds = customerDetailsRepo.findCustomerIdsEligibleForLapsedRenewalReminder(
                RENEWAL_REMINDER_MIN_GAP_DAYS,
                RENEWAL_REMINDER_MAX_PER_WEEK,
                RENEWAL_REMINDER_WINDOW_DAYS);

        if (userIds.isEmpty()) {
            logger.info("No customers eligible for resume-plan reminder (lapsed only, max {} per {}d, {}d gap)",
                    RENEWAL_REMINDER_MAX_PER_WEEK, RENEWAL_REMINDER_WINDOW_DAYS, RENEWAL_REMINDER_MIN_GAP_DAYS);
            return;
        }

        Set<String> sentTokens = new HashSet<>();
        int sent = 0;
        for (Long userId : userIds) {
            if (!preferencesAllowRenewalReminder(userId)) {
                continue;
            }
            List<UserDevice> devices = userDeviceRepository.findByUserIdAndIsActiveTrue(userId);
            if (devices.isEmpty()) {
                continue;
            }
            for (UserDevice device : devices) {
                String token = device.getDeviceToken();
                if (token == null || token.isBlank() || !sentTokens.add(token)) {
                    continue;
                }
                fcmService.sendNotification(device, title, message, notificationType);
                sent++;
            }
        }

        logger.info("Lapsed renewal reminders | eligibleProfiles={} | devicesSent={}", userIds.size(), sent);
    }

    private boolean preferencesAllowRenewalReminder(Long userId) {
        Optional<UserNotificationPreference> prefOpt = preferenceRepository.findByUserId(userId);
        if (prefOpt.isEmpty()) {
            return true;
        }
        UserNotificationPreference pref = prefOpt.get();
        return pref.isNotificationsEnabled() && pref.isRenewalReminderEnabled();
    }

    /**
     * Register a new device token for a user.
     * Prevents duplicate tokens and updates if exists.
     */
    @Transactional
    public UserDevice registerDevice(Long userId, String deviceToken, String platform, String appVersion) {
        // Check if token already exists for this user
        Optional<UserDevice> existing = userDeviceRepository
                .findByUserIdAndDeviceToken(userId, deviceToken);

        if (existing.isPresent()) {
            UserDevice device = existing.get();
            // Update existing device
            device.setActive(true);
            device.setPlatform(platform);
            device.setAppVersion(appVersion);
            device.markAsUsed();
            UserDevice saved = userDeviceRepository.save(device);
            logger.info("Device token updated | userId={} | deviceId={}", userId, saved.getId());
            return saved;
        }

        // Create new device
        UserDevice device = new UserDevice(userId, deviceToken, platform, appVersion);
        UserDevice saved = userDeviceRepository.save(device);
        
        // Create default notification preferences if they don't exist
        createDefaultPreferencesIfNeeded(userId);
        
        logger.info("New device registered | userId={} | deviceId={} | platform={}", 
                   userId, saved.getId(), platform);
        return saved;
    }

    /**
     * Deactivate a specific device token.
     */
    @Transactional
    public void deactivateDevice(Long userId, String deviceToken) {
        Optional<UserDevice> device = userDeviceRepository
                .findByUserIdAndDeviceToken(userId, deviceToken);
        
        if (device.isPresent()) {
            UserDevice dev = device.get();
            dev.setActive(false);
            userDeviceRepository.save(dev);
            logger.info("Device deactivated | userId={} | deviceId={}", userId, dev.getId());
        }
    }

    /**
     * Get or create notification preferences for a user.
     */
    @Transactional
    public UserNotificationPreference getOrCreatePreferences(Long userId) {
        return preferenceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserNotificationPreference pref = new UserNotificationPreference(userId);
                    return preferenceRepository.save(pref);
                });
    }

    /**
     * Update notification preferences for a user.
     */
    @Transactional
    public UserNotificationPreference updatePreferences(Long userId, UserNotificationPreference preferences) {
        UserNotificationPreference existing = getOrCreatePreferences(userId);
        
        // Update all preference fields
        existing.setNotificationsEnabled(preferences.isNotificationsEnabled());
        existing.setFoodReadyEnabled(preferences.isFoodReadyEnabled());
        existing.setMorningDeliveryEnabled(preferences.isMorningDeliveryEnabled());
        existing.setDispatchEnabled(preferences.isDispatchEnabled());
        existing.setBreakfastTipEnabled(preferences.isBreakfastTipEnabled());
        existing.setCustomizationEnabled(preferences.isCustomizationEnabled());
        existing.setRenewalReminderEnabled(preferences.isRenewalReminderEnabled());
        existing.setMarketingEnabled(preferences.isMarketingEnabled());
        
        return preferenceRepository.save(existing);
    }

    private List<UserDevice> selectEligibleDevices(
            List<UserDevice> devices,
            String notificationType) {
        boolean dedupeByPhone = ONCE_PER_PHONE_TYPES.contains(notificationType.toLowerCase());
        Map<Long, String> phoneByCustomerId = dedupeByPhone
                ? loadPhoneNumbersByCustomerId(devices)
                : Map.of();

        Map<String, UserDevice> bestDevicePerKey = new LinkedHashMap<>();

        for (UserDevice device : devices) {
            if (device == null || !device.isActive()) {
                continue;
            }

            String token = device.getDeviceToken();
            if (token == null || token.isBlank()) {
                continue;
            }

            if (!shouldSendNotification(device.getUserId(), notificationType)) {
                continue;
            }

            String dedupeKey = resolveDedupeKey(device, dedupeByPhone, phoneByCustomerId, token);
            if (dedupeKey == null || dedupeKey.isBlank()) {
                continue;
            }

            UserDevice existing = bestDevicePerKey.get(dedupeKey);
            if (existing == null || isPreferredDevice(device, existing)) {
                bestDevicePerKey.put(dedupeKey, device);
            }
        }

        return new ArrayList<>(bestDevicePerKey.values());
    }

    private String resolveDedupeKey(
            UserDevice device,
            boolean dedupeByPhone,
            Map<Long, String> phoneByCustomerId,
            String token) {
        if (!dedupeByPhone) {
            return token;
        }
        String phone = phoneByCustomerId.get(device.getUserId());
        return phone != null && !phone.isBlank() ? phone : token;
    }

    private Map<Long, String> loadPhoneNumbersByCustomerId(List<UserDevice> devices) {
        Set<Long> customerIds = devices.stream()
                .map(UserDevice::getUserId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> phoneByCustomerId = new HashMap<>();
        for (CustomerDetails customer : customerDetailsRepo.findAllById(customerIds)) {
            if (customer.getId() != null && customer.getPhoneNumber() != null) {
                phoneByCustomerId.put(customer.getId(), normalizePhone(customer.getPhoneNumber()));
            }
        }
        return phoneByCustomerId;
    }

    private String normalizePhone(String phone) {
        String digits = phone.replaceAll("\\D", "");
        if (digits.length() > 10) {
            return digits.substring(digits.length() - 10);
        }
        return digits;
    }

    private boolean isPreferredDevice(UserDevice candidate, UserDevice current) {
        if (candidate.getLastUsedAt() == null) {
            return false;
        }
        if (current.getLastUsedAt() == null) {
            return true;
        }
        return candidate.getLastUsedAt().isAfter(current.getLastUsedAt());
    }

    /**
     * Check if notification should be sent to user based on preferences and subscription.
     */
    private boolean shouldSendNotification(Long userId, String notificationType) {
        // Check if user has active subscription
        if (!hasActiveSubscription(userId)) {
            logger.debug("User does not have active subscription | userId={}", userId);
            return false;
        }

        // Get user preferences
        Optional<UserNotificationPreference> prefOpt = preferenceRepository.findByUserId(userId);
        if (prefOpt.isEmpty()) {
            // No preferences set, allow all by default
            return true;
        }

        UserNotificationPreference pref = prefOpt.get();

        // Check global notifications enabled
        if (!pref.isNotificationsEnabled()) {
            return false;
        }

        // Check specific notification type
        switch (notificationType.toLowerCase()) {
            case "food_ready":
                return pref.isFoodReadyEnabled();
            case "morning_delivery":
                return pref.isMorningDeliveryEnabled();
            case "dispatch":
                return pref.isDispatchEnabled();
            case "breakfast_tip":
                return pref.isBreakfastTipEnabled();
            case "customization_open":
            case "customization_reminder":
            case "last_reminder":
            case "customization_closed":
                return pref.isCustomizationEnabled();
            case "renewal_reminder":
                return pref.isRenewalReminderEnabled();
            case "marketing":
                return pref.isMarketingEnabled();
            default:
                // Unknown type, allow by default
                return true;
        }
    }

    /**
     * Check if user has an active subscription.
     */
    private boolean hasActiveSubscription(Long userId) {

        try {

            Optional<CustomerDetails> customerOpt =
                    customerDetailsRepo.findById(userId);

            if (customerOpt.isEmpty()) {
                return false;
            }

            CustomerDetails customer = customerOpt.get();

            boolean paymentSuccess = customer.isPaymentSuccess();

            Boolean cancelled = customer.getIsCancelled();

            return paymentSuccess &&
                    (cancelled == null || !cancelled);

        } catch (Exception e) {

            logger.error(
                    "Error checking subscription status for userId={}: {}",
                    userId,
                    e.getMessage());

            return false;
        }
    }   

    private void createDefaultPreferencesIfNeeded(Long userId) {
        if (preferenceRepository.findByUserId(userId).isEmpty()) {
            UserNotificationPreference pref = new UserNotificationPreference(userId);
            preferenceRepository.save(pref);
            logger.debug("Created default notification preferences for userId={}", userId);
        }
    }
}
