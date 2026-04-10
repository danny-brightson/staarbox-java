// package com.example.demo.util;

// public class MessageTemplates {

//     public static final String FOOD_READY = "Early start, your healthy meal is getting ready";

//     public static final String MORNING_DELIVERY = "Your box is already on its way";

//     public static final String CUSTOMIZATION_OPEN = "Your meal is open now, go make it yours";

//     public static final String REMINDER = "You haven’t customized yet";

//     public static final String LAST_REMINDER = "Last call, your meal will be locked soon";

//     public static final String CLOSED = "Customization is closed";

//     public static final String DISPATCH = "Your meal is on the way";
// }

package com.example.demo.util;

import java.util.Random;

/**
 * All push notification message templates for StarBox.
 * Each notification has 2 variants — one is picked randomly.
 */
public class MessageTemplates {

    private static final Random RANDOM = new Random();

    // ── Titles ──────────────────────────────────────────────────
    public static final String TITLE_FOOD_READY         = "🌅 Food Ready!";
    public static final String TITLE_MORNING_DELIVERY   = "🚀 Morning Delivery";
    public static final String TITLE_CUSTOM_OPEN        = "✏️ Customization Open";
    public static final String TITLE_REMINDER           = "⏰ Reminder";
    public static final String TITLE_LAST_REMINDER      = "🔴 Last Chance!";
    public static final String TITLE_CLOSED             = "🔒 Customization Closed";
    public static final String TITLE_RENEWAL            = "🔄 Resume Your Plan";
    public static final String TITLE_DISPATCH           = "🛵 On the Way!";
    public static final String TITLE_BREAKFAST_TIP      = "💡 Breakfast Tip";

    // ── Message Bodies (2 variants each) ────────────────────────

    private static final String[] FOOD_READY = {
        "Early start, your healthy meal is getting ready.",
        "Wake up, freshness is cooking just for you."
    };

    private static final String[] MORNING_DELIVERY = {
        "Your box is already on its way, fresh and made for you.",
        "Breakfast is ready, don't keep it waiting."
    };

    private static final String[] CUSTOMIZATION_OPEN = {
        "Your meal is open now, go make it yours.",
        "Today's meal is in your hands, set it your way."
    };

    private static final String[] CUSTOMIZATION_REMINDER = {
        "You haven't customized yet, your meal still has your name on it.",
        "Not done yet, take a moment and make it yours."
    };

    private static final String[] LAST_REMINDER = {
        "Almost time, finish your customization before it closes.",
        "Last call, your meal will be locked soon."
    };

    private static final String[] CLOSED = {
        "Customization is closed, your meal is now being prepared.",
        "Window closed! Your fresh meal is being packed for you."
    };

    private static final String[] RENEWAL = {
        "Your plan has paused, ready when you are.",
        "Pick it back up, your routine is waiting."
    };

    private static final String[] DISPATCH = {
        "It's on the move, your meal is almost there.",
        "Your healthy pick is on its way."
    };

    private static final String[] BREAKFAST_TIP = {
        "Start your day right, your body will thank you.",
        "A good morning meal makes everything easier."
    };

    // ── Public getters (random variant) ────────────────────────

    public static String getFoodReady()            { return pick(FOOD_READY); }
    public static String getMorningDelivery()      { return pick(MORNING_DELIVERY); }
    public static String getCustomizationOpen()    { return pick(CUSTOMIZATION_OPEN); }
    public static String getCustomizationReminder(){ return pick(CUSTOMIZATION_REMINDER); }
    public static String getLastReminder()         { return pick(LAST_REMINDER); }
    public static String getClosed()               { return pick(CLOSED); }
    public static String getRenewal()              { return pick(RENEWAL); }
    public static String getDispatch()             { return pick(DISPATCH); }
    public static String getBreakfastTip()         { return pick(BREAKFAST_TIP); }

    private static String pick(String[] variants) {
        return variants[RANDOM.nextInt(variants.length)];
    }
}