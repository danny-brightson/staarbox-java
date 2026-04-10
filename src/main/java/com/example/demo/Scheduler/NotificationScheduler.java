package com.example.demo.Scheduler;

import com.example.demo.service.NotificationService;
import com.example.demo.util.MessageTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    // ─────────────────────────────────────────────────────────
    // 1. FOOD READY — 5:00 AM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 0 5 * * ?", zone = "Asia/Kolkata")
    public void foodReady() {
        System.out.println("⏰ [CRON] Food Ready — 5:00 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_FOOD_READY,
            MessageTemplates.getFoodReady()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 2. MORNING DELIVERY — 6:00 AM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Kolkata")
    public void morningDelivery() {
        System.out.println("⏰ [CRON] Morning Delivery — 6:00 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_MORNING_DELIVERY,
            MessageTemplates.getMorningDelivery()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 3. OUT FOR DISPATCH — 6:30 AM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 30 6 * * ?", zone = "Asia/Kolkata")
    public void outForDispatch() {
        System.out.println("⏰ [CRON] Out for Dispatch — 6:30 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_DISPATCH,
            MessageTemplates.getDispatch()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 4. BREAKFAST TIP — 7:00 AM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 0 7 * * ?", zone = "Asia/Kolkata")
    public void breakfastTip() {
        System.out.println("⏰ [CRON] Breakfast Tip — 7:00 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_BREAKFAST_TIP,
            MessageTemplates.getBreakfastTip()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 5. CUSTOMIZATION OPEN — 9:30 AM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 30 9 * * ?", zone = "Asia/Kolkata")
    public void customizationOpen() {
        System.out.println("⏰ [CRON] Customization Open — 9:30 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_CUSTOM_OPEN,
            MessageTemplates.getCustomizationOpen()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 6. RENEWAL REMINDER — 10:00 AM
    //    (sendToAllUsers here — filter paused users in service
    //     if you have a plan_status column in your user table)
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Kolkata")
    public void renewalReminder() {
        System.out.println("⏰ [CRON] Renewal Reminder — 10:00 AM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_RENEWAL,
            MessageTemplates.getRenewal()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 7. CUSTOMIZATION REMINDER — 2:00 PM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 0 14 * * ?", zone = "Asia/Kolkata")
    public void customizationReminder() {
        System.out.println("⏰ [CRON] Customization Reminder — 2:00 PM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_REMINDER,
            MessageTemplates.getCustomizationReminder()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 8. LAST REMINDER — 6:45 PM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 45 18 * * ?", zone = "Asia/Kolkata")
    public void lastReminder() {
        System.out.println("⏰ [CRON] Last Reminder — 6:45 PM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_LAST_REMINDER,
            MessageTemplates.getLastReminder()
        );
    }

    // ─────────────────────────────────────────────────────────
    // 9. CUSTOMIZATION CLOSED — 7:30 PM
    // ─────────────────────────────────────────────────────────
    @Scheduled(cron = "0 30 19 * * ?", zone = "Asia/Kolkata")
    public void customizationClosed() {
        System.out.println("⏰ [CRON] Customization Closed — 7:30 PM");
        notificationService.sendToAllUsers(
            MessageTemplates.TITLE_CLOSED,
            MessageTemplates.getClosed()
        );
    }
}