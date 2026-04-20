package com.example.demo.Scheduler;

import com.example.demo.service.SmsNotificationService;
import com.example.demo.util.MessageTemplates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationScheduler {

    @Autowired
    private SmsNotificationService smsNotificationService;

    // ─────────────────────────────────────────────
    // 🔄 DAILY RESET (12:00 AM)
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Kolkata")
    public void resetSmsCount() {
        System.out.println("🔄 Resetting SMS count...");
        smsNotificationService.resetDailySmsCount();
    }

    // ─────────────────────────────────────────────
    // 🍳 FOOD READY — 5:00 AM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 5 * * ?", zone = "Asia/Kolkata")
    public void foodReady() {
        String msg = MessageTemplates.getFoodReady();
        System.out.println("⏰ FOOD READY SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "FOOD_READY");
    }

    // ─────────────────────────────────────────────
    // 🚀 MORNING DELIVERY — 6:00 AM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 6 * * ?", zone = "Asia/Kolkata")
    public void morningDelivery() {
        String msg = MessageTemplates.getMorningDelivery();
        System.out.println("⏰ MORNING DELIVERY SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "DELIVERY");
    }

    // ─────────────────────────────────────────────
    // 🛵 DISPATCH — 6:30 AM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 30 6 * * ?", zone = "Asia/Kolkata")
    public void dispatch() {
        String msg = MessageTemplates.getDispatch();
        System.out.println("⏰ DISPATCH SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "DISPATCH");
    }

    // ─────────────────────────────────────────────
    // 💡 BREAKFAST TIP — 7:00 AM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 7 * * ?", zone = "Asia/Kolkata")
    public void breakfastTip() {
        String msg = MessageTemplates.getBreakfastTip();
        System.out.println("⏰ BREAKFAST TIP SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "TIP");
    }

    // ─────────────────────────────────────────────
    // ✏️ CUSTOMIZATION OPEN — 9:30 AM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 30 9 * * ?", zone = "Asia/Kolkata")
    public void customizationOpen() {
        String msg = MessageTemplates.getCustomizationOpen();
        System.out.println("⏰ CUSTOMIZATION OPEN SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "CUSTOM_OPEN");
    }

    // ─────────────────────────────────────────────
    // 🔄 RENEWAL — 10:00 AM (HIGH PRIORITY)
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Kolkata")
    public void renewalReminder() {
        String msg = MessageTemplates.getRenewal();
        System.out.println("⏰ RENEWAL SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "RENEWAL");
    }

    // ─────────────────────────────────────────────
    // ⏰ REMINDER — 2:00 PM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 0 14 * * ?", zone = "Asia/Kolkata")
    public void reminder() {
        String msg = MessageTemplates.getCustomizationReminder();
        System.out.println("⏰ REMINDER SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "REMINDER");
    }

    // ─────────────────────────────────────────────
    // 🔴 LAST REMINDER — 6:45 PM (HIGH PRIORITY)
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 45 18 * * ?", zone = "Asia/Kolkata")
    public void lastReminder() {
        String msg = MessageTemplates.getLastReminder();
        System.out.println("⏰ LAST REMINDER SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "LAST_REMINDER");
    }

    // ─────────────────────────────────────────────
    // 🔒 CLOSED — 7:30 PM
    // ─────────────────────────────────────────────
    @Scheduled(cron = "0 30 19 * * ?", zone = "Asia/Kolkata")
    public void closed() {
        String msg = MessageTemplates.getClosed();
        System.out.println("⏰ CLOSED SMS");
        smsNotificationService.sendSmartSmsToAllUsers(msg, "CLOSED");
    }
}