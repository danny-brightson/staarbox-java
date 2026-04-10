    // package com.example.demo.rest;

    // import java.time.LocalDate;
    // import java.time.LocalDateTime;
    // import java.util.HashMap;
    // import java.util.Map;

    // import org.springframework.beans.factory.annotation.Autowired;
    // import org.springframework.http.ResponseEntity;
    // import org.springframework.web.bind.annotation.PostMapping;
    // import org.springframework.web.bind.annotation.RequestMapping;
    // import org.springframework.web.bind.annotation.RequestParam;
    // import org.springframework.web.bind.annotation.RestController;

    // import com.example.demo.entity.Partner;
    // import com.example.demo.entity.PromoCode;
    // import com.example.demo.repo.PartnerRepo;
    // import com.example.demo.repo.PromoCodeRepo;
    // import com.example.demo.service.PromoCodeService;

    // @RestController
    // @RequestMapping("/api/promo")
    // public class PromoCodeController {

    //     @Autowired
    //     private PromoCodeService promoCodeService;

    //     @Autowired
    //     private PartnerRepo partnerRepo;        // ✅ inject

    //     @Autowired
    //     private PromoCodeRepo promoCodeRepo;    // ✅ inject

    //     // Customer — free trial apply
    //     @PostMapping("/apply")
    //     public ResponseEntity<Map<String, Object>> applyPromo(
    //             @RequestParam String promoCode,
    //             @RequestParam Long customerId) {

    //         Map<String, Object> result = promoCodeService.applyPromoCode(promoCode, customerId);

    //         if ((Boolean) result.get("success")) {
    //             return ResponseEntity.ok(result);
    //         } else {
    //             return ResponseEntity.badRequest().body(result);
    //         }
    //     }

    //     // Admin — partner + promo code create
    //     @PostMapping("/partner/create")
    //     public ResponseEntity<Map<String, Object>> createPartner(
    //             @RequestParam String name,
    //             @RequestParam String phone,
    //             @RequestParam String email,
    //             @RequestParam String promoCode,     // Admin manually குடுக்கற code
    //             @RequestParam Integer usageLimit,
    //             @RequestParam String expiryDate) {

    //         Map<String, Object> result = new HashMap<>();

    //         // Duplicate code check
    //         if (promoCodeRepo.findValidPromo(promoCode).isPresent()) {
    //             result.put("success", false);
    //             result.put("message", "Promo code already exists!");
    //             return ResponseEntity.badRequest().body(result);
    //         }

    //         // 1. Partner save
    //         Partner partner = new Partner();
    //         partner.setName(name);
    //         partner.setPhone(phone);
    //         partner.setEmail(email);
    //         partner.setIsActive(true);
    //         partner.setCreatedBy("admin");
    //         partner.setCreatedTime(LocalDateTime.now());
    //         Partner savedPartner = partnerRepo.save(partner);

    //         // 2. PromoCode save
    //         PromoCode promo = new PromoCode();
    //         promo.setCode(promoCode);
    //         promo.setPartnerId(savedPartner.getId());
    //         promo.setIsActive(true);
    //         promo.setUsageLimit(usageLimit);
    //         promo.setUsedCount(0);
    //         promo.setExpiryDate(LocalDate.parse(expiryDate));
    //         promo.setCreatedBy("admin");
    //         promo.setCreatedTime(LocalDateTime.now());
    //         promoCodeRepo.save(promo);

    //         result.put("success", true);
    //         result.put("partnerId", savedPartner.getId());
    //         result.put("partnerName", savedPartner.getName());
    //         result.put("promoCode", promoCode);
    //         result.put("expiryDate", expiryDate);
    //         result.put("usageLimit", usageLimit);

    //         return ResponseEntity.ok(result);
    //     }
    // }