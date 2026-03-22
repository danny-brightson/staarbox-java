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

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping("/create-order")
	public Map<String, Object> createOrder(@RequestParam int amount,@RequestParam long customerId) {
	    String currency = "INR";
	    String receipt = "ORDER_" + System.currentTimeMillis();
	    Map<String, Object> response = new HashMap<>();
	    try {
	        response = paymentService.createOrder(amount, currency, receipt,customerId);
	    } catch (Exception e) {
	        response.put("error", e.getMessage());
	    }
	    return response;
	}


//	@PostMapping("/verify")
//	public ResponseEntity<String> verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
//			@RequestParam String signature, @RequestParam int cusId,  @RequestParam(required = false) String promoCode)  {
//		boolean isValid = paymentService.verifySignature(orderId, paymentId, signature);
//	
//		
//		if (isValid) {
//			PaymentDetails payment = new PaymentDetails();
//			payment.setOrderId(orderId);
//			payment.setCusId(cusId);
//			payment.setPaymentId(paymentId);
//			payment.setStatus("SUCCESS");
//			payment.setStatusId((long) 1);
//			payment.setCreatedBy("User");
//			payment.setCreatedTime(LocalDateTime.now());
//			paymentDetailsrepo.save(payment);
//			LocalDateTime nextRenewDate = LocalDateTime.now().plusDays(30);
//			int customerStatus = 5;
//			System.out.println(payment.getStatus());
//			customerDetailsRepo.updatePaymentStatus(isValid, nextRenewDate, cusId, orderId,customerStatus, LocalDateTime.now());
//		}
//
//		else {
//			PaymentDetails payment = new PaymentDetails();
//			payment.setOrderId(orderId);
//			payment.setCusId(cusId);
//			payment.setPaymentId(paymentId);
//			payment.setStatus("failed");
//			payment.setStatusId((long) 1);
//			payment.setCreatedBy("User");
//			payment.setCreatedTime(LocalDateTime.now());
//			paymentDetailsrepo.save(payment);
//			System.out.println(payment.getStatus());
//		}
//		System.out.println(isValid);
//		return isValid ? ResponseEntity.ok("Payment Verified")
//				: ResponseEntity.status(400).body("Payment Verification Failed");
//	}
	
	@PostMapping("/verify")
	public ResponseEntity<List<Map<String, Object>>> verifyPayment(
			@RequestParam String orderId, @RequestParam String paymentId,
			@RequestParam String signature, @RequestParam List<Integer> customerIds,  
			@RequestParam(required = false) String promoCode,@RequestParam Long amount,
			@RequestParam Boolean isRenewed,@RequestParam Boolean isFromWallet) {

	    boolean isValid = paymentService.verifySignature(
	    		orderId, paymentId, signature);

	    List<Map<String, Object>> results = new ArrayList<>();
	    
//	 boolean   isrenewed = false;
//	 LocalDateTime renewedDate = null;
//	 int customerStatus = 0;
	 
	    
	    

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

	        if (isValid) {

	            payment.setStatus("SUCCESS");
	            paymentDetailsrepo.save(payment);

	            // ==============================
	            //  WALLET PAYMENT LOGIC ONLY
	            // ==============================
	            if (Boolean.TRUE.equals(isFromWallet)) {

	                BigDecimal paidAmount = new BigDecimal(amount);

	                Optional<Wallet> existingWallet = walletRepository.findByCustomerId(cusId.longValue());

	                if (existingWallet.isPresent()) {
	                    Wallet wallet = existingWallet.get();
	                    wallet.setAmount(wallet.getAmount().add(paidAmount));
	                    wallet.setLastPaymentDate(LocalDateTime.now());
	                    wallet.setLastpaidAmount(amount);
	                    walletRepository.save(wallet);
	                } else {
	                    Wallet newWallet = new Wallet();
	                    newWallet.setCustomerId(cusId.longValue());
	                    newWallet.setAmount(paidAmount);
	                    newWallet.setLastPaymentDate(LocalDateTime.now());
	                    newWallet.setLastpaidAmount(amount);
	                    walletRepository.save(newWallet);
	                }

	                // IMPORTANT: Skip subscription update
	                result.put("status", "Wallet Updated Successfully");
	            }

	            // ==================================
	            //  SUBSCRIPTION / RENEWAL LOGIC
	            // ==================================
	            else {

	                boolean isrenewed = false;
	                LocalDateTime renewedDate = null;
	                int customerStatus;

	                if (Boolean.TRUE.equals(isRenewed)) {
	                    isrenewed = true;
	                    renewedDate = LocalDateTime.now();
	                    customerStatus = 6; // Renewed
	                } else {
	                    customerStatus = 5; // New Subscription
	                }

	                //  Calculate next renewal (26 delivery days excluding Sunday)
	                LocalDate today = LocalDate.now();
	                DayOfWeek day = today.getDayOfWeek();

	                LocalDate nextRenewalLocalDate;
	                if (day == DayOfWeek.FRIDAY) {
	                    nextRenewalLocalDate = calculateNextRenewalDate(today.plusDays(3));
	                } else {
	                    nextRenewalLocalDate = calculateNextRenewalDate(today.plusDays(2));
	                }

	                LocalDateTime nextRenewDate = nextRenewalLocalDate.atStartOfDay();

	                int customized = 0;

	                customerDetailsRepo.updatePaymentStatus(
	                        true,
	                        nextRenewDate,
	                        cusId,
	                        orderId,
	                        customerStatus,
	                        LocalDateTime.now(),   // lastPaymentTime
	                        customized,
	                        isrenewed,
	                        renewedDate
	                );

	                result.put("status", "Subscription Activated");
	            }

	        } else {
	            payment.setStatus("FAILED");
	            paymentDetailsrepo.save(payment);
	            result.put("status", "Payment Verification Failed");
	        }
	        results.add(result);
	    }
	    return ResponseEntity.ok(results);
	}
//
//	@PostMapping("/verify")
//	public ResponseEntity<List<Map<String, Object>>> verifyPayment(
//			@RequestParam String orderId, @RequestParam String paymentId,
//			@RequestParam String signature, @RequestParam List<Integer> customerIds
//			,  
//			@RequestParam(required = false) String promoCode,@RequestParam Long amount,
//			@RequestParam Boolean isRenewed,@RequestParam Boolean isFromWallet
//			) {
//
//	    boolean isValid = paymentService.verifySignature(
//	    		orderId, paymentId, signature);
//
//	    List<Map<String, Object>> results = new ArrayList<>();
//	    
//	 boolean   isrenewed = false;
//	 LocalDateTime renewedDate = null;
//	 
//	 
//	    
//	    
//
//	    for (Integer cusId : customerIds) {
//	        Map<String, Object> result = new HashMap<>();
//	        result.put("customerId", cusId);
//	        result.put("orderId", orderId);
//
//	        PaymentDetails payment = new PaymentDetails();
//	        payment.setOrderId(orderId);
//	        payment.setCusId(cusId);
//	        payment.setPaymentId(paymentId);
//	        payment.setStatusId(1L);
//	        payment.setCreatedBy("User");
//	        payment.setCreatedTime(LocalDateTime.now());
//
//	        if (isValid) {
//	            payment.setStatus("SUCCESS");
//	            paymentDetailsrepo.save(payment);
//          
//	            if(isFromWallet) {
//	         // ✅ Wallet Update Logic
//	            BigDecimal paidAmount = new BigDecimal(amount); // get real amount dynamically
//
//	            Optional<Wallet> existingWallet = walletRepository.findByCustomerId(cusId.longValue());
//
//            if (existingWallet.isPresent()) {
//
//	                Wallet wallet = existingWallet.get();
//
//	                BigDecimal updatedAmount = wallet.getAmount().add(paidAmount);
//	                wallet.setAmount(updatedAmount);
//	                wallet.setLastPaymentDate(LocalDateTime.now());
//	                wallet.setLastpaidAmount(amount);
//
//	                walletRepository.save(wallet);
//
//            } else {
//
//	                Wallet newWallet = new Wallet();
//	                newWallet.setCustomerId(cusId.longValue());
//	                newWallet.setAmount(paidAmount);
//	                newWallet.setLastPaymentDate(LocalDateTime.now());
//	                newWallet.setLastpaidAmount(amount);
//
//	                walletRepository.save(newWallet);
//	            }
//	            }
//	            
//	            if (isRenewed==true) {
//	            	isrenewed = true;
//	            	renewedDate = LocalDateTime.now();
//	            	customerStatus = 6;
//	            }
//	            else {
//	            	isrenewed = false;
//	            	customerStatus = 5;
//	            }
//
//	           // LocalDateTime nextRenewDate = LocalDateTime.now().plusDays(30);
//	            
//	         // ✅ Calculate next renewal using 26 delivery days (excluding Sundays)
//	            LocalDate today = LocalDate.now();
//	            DayOfWeek day = today.getDayOfWeek();
//	            LocalDate nextRenewalLocalDate;
//	    	    if (day == DayOfWeek.FRIDAY) {
//	    	    	  nextRenewalLocalDate = calculateNextRenewalDate(today.plusDays(3));
//	    	    }
//	    	    else {
//	    	    	 nextRenewalLocalDate = calculateNextRenewalDate(today.plusDays(2));
//	    	    }
//	            
//
//	            // ✅ Convert to LocalDateTime (start of day)
//	            LocalDateTime nextRenewDate = nextRenewalLocalDate.atStartOfDay();
//
//	            int customerStatus = 5;
//	            int customized=0;
//	            customerDetailsRepo.updatePaymentStatus(
//	                    true, nextRenewDate, cusId, orderId, customerStatus, 
//	                    LocalDateTime.now(),customized,isrenewed,renewedDate
//	            );
//
//	            result.put("status", "Payment Verified");
//	        } else {
//	            payment.setStatus("FAILED");
//	            paymentDetailsrepo.save(payment);
//	            result.put("status", "Payment Verification Failed");
//	        }
//
//	        results.add(result);
//	    }
//
//	    return ResponseEntity.ok(results);
//	}
//	
	public LocalDate calculateNextRenewalDate(LocalDate startDate) {

	    int deliveryDays = 0;
	    LocalDate renewalDate = startDate;

	    while (deliveryDays < 26) {
	        renewalDate = renewalDate.plusDays(1);

	        // ✅ Skip Sundays
	        if (renewalDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
	            deliveryDays++;
	        }
	    }

	    return renewalDate;
	}

}
