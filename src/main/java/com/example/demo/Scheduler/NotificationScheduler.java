package com.example.demo.Scheduler;

import com.example.demo.repo.UserDeviceRepository;
import java.util.List;
import com.example.demo.service.NotificationService;
import com.example.demo.util.MessageTemplates;
import com.example.demo.util.MessageTemplates.NotificationData;
import com.example.demo.util.NotificationSchedulePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Scheduled notifications for StarBox.
 * All times are in Asia/Kolkata timezone.
 * Respects user preferences and subscription status.
 */
@Component
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    /**
     * 1. FOOD READY - 5:00 AM
     */
    @Scheduled(cron = "0 0 5 * * ?", zone = "Asia/Kolkata")
    public void foodReady() {
        logger.info("CRON triggered: Food Ready - 5:00 AM");
        if (!NotificationSchedulePolicy.shouldSendDeliveryNotifications()) {
            logger.info("Skipping Food Ready on Sunday (no delivery day)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getFoodReady();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in foodReady scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 2. MORNING DELIVERY - 6:00 AM
     */
    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Kolkata")
    public void morningDelivery() {
        logger.info("CRON triggered: Morning Delivery - 6:00 AM");
        if (!NotificationSchedulePolicy.shouldSendDeliveryNotifications()) {
            logger.info("Skipping Morning Delivery on Sunday (no delivery day)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getMorningDelivery();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in morningDelivery scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 3. OUT FOR DISPATCH - 6:30 AM
     */
    @Scheduled(cron = "0 30 6 * * ?", zone = "Asia/Kolkata")
    public void outForDispatch() {
        logger.info("CRON skipped: Out for Dispatch disabled (meal on the move notification)");
    }

    /**
     * 4. BREAKFAST TIP - 7:00 AM
     */
    @Scheduled(cron = "0 0 7 * * ?", zone = "Asia/Kolkata")
    public void breakfastTip() {
        logger.info("CRON triggered: Breakfast Tip - 7:00 AM");
        if (!NotificationSchedulePolicy.shouldSendDeliveryNotifications()) {
            logger.info("Skipping Breakfast Tip on Sunday (no delivery day)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getBreakfastTip();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in breakfastTip scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 5. CUSTOMIZATION OPEN - 9:30 AM
     */
    @Scheduled(cron = "0 30 9 * * ?", zone = "Asia/Kolkata")
    public void customizationOpen() {
        logger.info("CRON triggered: Customization Open - 9:30 AM");
        if (!NotificationSchedulePolicy.shouldSendCustomizationNotifications()) {
            logger.info("Skipping Customization Open on Saturday (delivery day, no customization reminders)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getCustomizationOpen();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in customizationOpen scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 6. RENEWAL REMINDER - 10:00 AM
     */
    @Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Kolkata")
    public void renewalReminder() {
        logger.info("CRON triggered: Lapsed subscription / resume plan reminder - 10:00 AM");
        try {
            NotificationData data = MessageTemplates.getRenewal();
            notificationService.sendLapsedSubscriptionRenewalReminders(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in renewalReminder scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 7. CUSTOMIZATION REMINDER - 2:00 PM
     */
    @Scheduled(cron = "0 0 14 * * ?", zone = "Asia/Kolkata")
    public void customizationReminder() {
        logger.info("CRON triggered: Customization Reminder - 2:00 PM");
        if (!NotificationSchedulePolicy.shouldSendCustomizationNotifications()) {
            logger.info("Skipping Customization Reminder on Saturday (delivery day, no customization reminders)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getCustomizationReminder();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in customizationReminder scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 8. LAST REMINDER - 6:45 PM
     */
    @Scheduled(cron = "0 45 18 * * ?", zone = "Asia/Kolkata")
    public void lastReminder() {
        logger.info("CRON triggered: Last Reminder - 6:45 PM");
        if (!NotificationSchedulePolicy.shouldSendCustomizationNotifications()) {
            logger.info("Skipping Last Reminder on Saturday (delivery day, no customization reminders)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getLastReminder();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in lastReminder scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * 9. CUSTOMIZATION CLOSED - 7:30 PM
     */
    @Scheduled(cron = "0 30 19 * * ?", zone = "Asia/Kolkata")
    public void customizationClosed() {
        logger.info("CRON triggered: Customization Closed - 7:30 PM");
        if (!NotificationSchedulePolicy.shouldSendCustomizationNotifications()) {
            logger.info("Skipping Customization Closed on Saturday (delivery day, no customization reminders)");
            return;
        }
        try {
            NotificationData data = MessageTemplates.getClosed();
            notificationService.sendToAllUsers(data.title, data.message, data.type);
        } catch (Exception e) {
            logger.error("Error in customizationClosed scheduler: {}", e.getMessage(), e);
        }
    }

    /**
     * Hard-delete device records that have been inactive for 30+ days — Daily 3:00 AM.
     * Soft-deactivation (failureCount >= 3) already happens in FCMService on each send.
     */
    @Transactional
    @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Kolkata")
    public void cleanupInactiveDevices() {
        logger.info("CRON triggered: Cleanup Inactive Devices - 3:00 AM");
        try {
            java.time.LocalDateTime cutoff = java.time.LocalDateTime.now().minusDays(30);
            List<com.example.demo.entity.UserDevice> stale = userDeviceRepository
                    .findAll()
                    .stream()
                    .filter(d -> !d.isActive()
                            && d.getLastUsedAt() != null
                            && d.getLastUsedAt().isBefore(cutoff))
                    .collect(java.util.stream.Collectors.toList());

            if (!stale.isEmpty()) {
                userDeviceRepository.deleteAll(stale);
                logger.info("Deleted {} stale inactive device records (last used >30 days ago)", stale.size());
            } else {
                logger.info("No stale devices to clean up");
            }
        } catch (Exception e) {
            logger.error("Error in cleanupInactiveDevices: {}", e.getMessage(), e);
        }
    }
}
