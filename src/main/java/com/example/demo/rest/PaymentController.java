package com.example.demo.rest;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PaymentDetails;
import com.example.demo.entity.Wallet;
import com.example.demo.repo.AvailablePromoCodeRepo;
import com.example.demo.repo.CheckoutDataRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.PaymentDetailsrepo;
import com.example.demo.repo.PricePerPackDetailsRepository;
import com.example.demo.repo.WalletRepository;
import com.example.demo.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    private PaymentDetailsrepo paymentDetailsrepo;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerDetailsRepo customerDetailsRepo;

    @Autowired
    private AvailablePromoCodeRepo availablePromoCodeRepo;

    @Autowired
    private CheckoutDataRepo checkoutDataRepo;

    @Autowired
    private PricePerPackDetailsRepository pricePerPackDetailsRepo;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(
            @RequestParam int amount,
            @RequestParam long customerId) {

        String currency = "INR";
        String receipt = "ORDER_" + System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        try {
            response = paymentService.createOrder(amount, currency, receipt, customerId);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/verify")
    public ResponseEntity<List<Map<String, Object>>> verifyPayment(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature,
            @RequestParam List<Integer> customerIds,
            @RequestParam(required = false) String promoCode,
            @RequestParam Long amount,
            @RequestParam Boolean isRenewed,
            @RequestParam Boolean isFromWallet) {

        // Real signature validation
        // boolean isValid = paymentService.verifySignature(orderId, paymentId, signature);
		boolean isValid = true; // For testing, set to true. Replace with actual validation in production.
		
        List<Map<String, Object>> results = new ArrayList<>();

        for (Integer cusId : customerIds) {

            Map<String, Object> result = new HashMap<>();
            result.put("customerId", cusId);
            result.put("orderId", orderId);

            PaymentDetails payment = new PaymentDetails();
            payment.setOrderId(orderId);
            payment.setCusId(cusId);
            payment.setPaymentId(paymentId);
            payment.setStatusId(1L);
            payment.setCreatedBy("User");
            payment.setCreatedTime(LocalDateTime.now());

            if (!isValid) {
                payment.setStatus("FAILED");
                paymentDetailsrepo.save(payment);
                result.put("status", "Payment Verification Failed");
                results.add(result);
                continue;
            }

            payment.setStatus("SUCCESS");
            paymentDetailsrepo.save(payment);

            // Step 1: Customer ID -> districtId & packageId
            Integer districtId = customerDetailsRepo
                    .findDistrictIdByCustomerId(cusId.longValue());
            Long packageId = customerDetailsRepo
                    .getPackageId(cusId.longValue());

            if (districtId == null || packageId == null) {
                result.put("status", "District or Package not found for customer");
                results.add(result);
                continue;
            }

            // Step 2: minAmount
            BigDecimal minAmount = pricePerPackDetailsRepo
                    .findMinAmount(packageId.intValue(), districtId);

            if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) <= 0) {
                result.put("status", "Min amount not found");
                results.add(result);
                continue;
            }

            // Step 3: Wallet balance
            BigDecimal walletBalance = BigDecimal.ZERO;
            Optional<Wallet> walletOpt = walletRepository
                    .findByCustomerId(cusId.longValue());
            if (walletOpt.isPresent()) {
                walletBalance = walletOpt.get().getAmount();
            }

            BigDecimal paidAmount = new BigDecimal(amount);

            // Step 4: isFromWallet=true -> wallet update
            if (isFromWallet) {
                if (walletOpt.isPresent()) {
                    Wallet wallet = walletOpt.get();
                    BigDecimal updatedAmount = walletBalance.add(paidAmount);
                    wallet.setAmount(updatedAmount);
                    wallet.setLastPaymentDate(LocalDateTime.now());
                    wallet.setLastCustomizedAmount(paidAmount);
                    walletRepository.save(wallet);
                    walletBalance = updatedAmount;
                } else {
                    Wallet newWallet = new Wallet();
                    newWallet.setCustomerId(cusId.longValue());
                    newWallet.setAmount(paidAmount);
                    newWallet.setLastPaymentDate(LocalDateTime.now());
                    newWallet.setLastCustomizedAmount(paidAmount);
                    walletRepository.save(newWallet);
                    walletBalance = paidAmount;
                }
            }

            // Step 5: Total = wallet + paid
            BigDecimal totalAmount = isFromWallet
                    ? walletBalance
                    : walletBalance.add(paidAmount);

            // Step 6: Days calculate
            BigDecimal[] division = totalAmount.divideAndRemainder(minAmount);
            int days = division[0].intValue();
            BigDecimal remainingAmount = division[1];

            // Step 7: Remaining amount wallet-ல save
            Optional<Wallet> freshWalletOpt = walletRepository
                    .findByCustomerId(cusId.longValue());
            if (freshWalletOpt.isPresent()) {
                Wallet wallet = freshWalletOpt.get();
                wallet.setAmount(remainingAmount);
                wallet.setLastPaymentDate(LocalDateTime.now());
                walletRepository.save(wallet);
            } else {
                if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
                    Wallet newWallet = new Wallet();
                    newWallet.setCustomerId(cusId.longValue());
                    newWallet.setAmount(remainingAmount);
                    newWallet.setLastPaymentDate(LocalDateTime.now());
                    walletRepository.save(newWallet);
                }
            }

            // Step 8: Sunday skip -> renewal date
            LocalDate today = LocalDate.now();
            LocalDate renewalLocalDate = calculateRenewalFromDays(today, days);
            LocalDateTime nextRenewDate = renewalLocalDate.atStartOfDay();

            // Step 9: Customer status
            boolean isrenewed = false;
            LocalDateTime renewedDate = null;
            int customerStatus;

            if (isRenewed) {
                isrenewed = true;
                renewedDate = LocalDateTime.now();
                customerStatus = 6;
            } else {
                customerStatus = 5;
            }

            int customized = 0;
            customerDetailsRepo.updatePaymentStatus(
                    true,
                    nextRenewDate,
                    cusId,
                    orderId,
                    customerStatus,
                    LocalDateTime.now(),
                    customized,
                    isrenewed,
                    renewedDate
            );

            result.put("status", "Payment Verified");
            results.add(result);
        }

        return ResponseEntity.ok(results);
    }

    public LocalDate calculateRenewalFromDays(LocalDate startDate, int days) {
        LocalDate date = startDate;
        int addedDays = 0;

        while (addedDays < days) {
            date = date.plusDays(1);
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                addedDays++;
            }
        }

        return date;
    }
}