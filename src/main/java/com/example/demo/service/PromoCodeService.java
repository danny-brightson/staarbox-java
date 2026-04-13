// package com.example.demo.service;

// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.entity.PromoCode;
// import com.example.demo.repo.CustomerDetailsRepo;
// import com.example.demo.repo.PromoCodeRepo;

// @Service
// public class PromoCodeService {

//     @Autowired
//     private PromoCodeRepo promoCodeRepo;

//     @Autowired
//     private CustomerDetailsRepo customerDetailsRepo;

//     public Map<String, Object> applyPromoCode(String code, Long customerId) {

//         Map<String, Object> result = new HashMap<>();

//         // 🔹 1. Get customer
//         var customerOpt = customerDetailsRepo.findById(customerId);

//         if (customerOpt.isEmpty()) {
//             result.put("success", false);
//             result.put("message", "Customer not found");
//             return result;
//         }

//         var customer = customerOpt.get();

//         // 🔴 2. Already used promo (EXISTING LOGIC)
//         String alreadyUsed = customerDetailsRepo.getPromoCodeUsed(customerId);
//         if (alreadyUsed != null && !alreadyUsed.isEmpty()) {
//             result.put("success", false);
//             result.put("message", "Promo already used");
//             return result;
//         }

//         // 3. NEW CHECK (VERY IMPORTANT)
//         // block renewal / paid users
//         if (Boolean.TRUE.equals(customer.isPaymentSuccess()) 
//                 || Boolean.TRUE.equals(customer.isRenewed())) {

//             result.put("success", false);
//             result.put("message", "Promo only for new users");
//             return result;
//         }

//         // 4. NEW CHECK (extra safety)
//         // ❌ already activated account
//         if (customerDetailsRepo.findNextRenewalDateByCustomerId(customerId) != null) {
//             result.put("success", false);
//             result.put("message", "Promo not allowed after activation");
//             return result;
//         }

//         // 🔹 5. Promo validation
//         Optional<PromoCode> promoOpt = promoCodeRepo.findValidPromo(code);
//         if (promoOpt.isEmpty()) {
//             result.put("success", false);
//             result.put("message", "Invalid or expired promo code");
//             return result;
//         }

//         // 🔹 6. Apply free trial
//         LocalDateTime freeTrialEndDate = LocalDateTime.now().plusMonths(1);

//         customerDetailsRepo.applyFreeTrial(customerId, freeTrialEndDate, code);
//         promoCodeRepo.incrementUsedCount(code);

//         result.put("success", true);
//         result.put("message", "Free trial activated for 1 month!");
//         result.put("freeTrialEndDate", freeTrialEndDate.toString());

//         return result;
//     }
// }