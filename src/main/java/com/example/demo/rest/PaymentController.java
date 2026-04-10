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
import com.example.demo.projection.CustomerPackDistrictProjection;
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
	public Map<String, Object> createOrder(@RequestParam int amount, @RequestParam long customerId) {

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
	public ResponseEntity<List<Map<String, Object>>> verifyPayment(@RequestParam String orderId,
			@RequestParam String paymentId, @RequestParam String signature, @RequestParam List<Integer> customerIds,
			@RequestParam(required = false) String promoCode, @RequestParam Long amount,
			@RequestParam Boolean isRenewed, @RequestParam Boolean isFromWallet) {

		boolean isValid = paymentService.verifySignature(orderId, paymentId, signature);

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

			if (isValid) {
				payment.setStatus("SUCCESS");
				paymentDetailsrepo.save(payment);
				Optional<Wallet> existingWallet = walletRepository.findByCustomerId(cusId.longValue());
				BigDecimal walletBalance;

				if (Boolean.TRUE.equals(isRenewed)) {
					BigDecimal rate = customerDetailsRepo.findRateByCustomerId(cusId.longValue());

					Wallet wallet = existingWallet.orElseThrow(() -> 
				    new RuntimeException("Wallet not found for customer: " + cusId)
				);

					BigDecimal updatedAmount = wallet.getAmount().add(rate);
					wallet.setAmount(updatedAmount);
					wallet.setLastPaymentDate(LocalDateTime.now());
					// wallet.setLastpaidAmount(paidAmount);

					walletRepository.save(wallet);
					walletBalance = updatedAmount;
					result.put("status", "Payment Verified");

				}

				else if (Boolean.TRUE.equals(isFromWallet)) {
					CustomerPackDistrictProjection res = customerDetailsRepo
							.findPackAndDistrictByCustomerId(cusId.longValue());

					LocalDate currentRenewDate = customerDetailsRepo.findNextRenewalDateByCustomerId(cusId);
					if (res == null) {
						throw new RuntimeException("Customer not found");
					}
					Integer packId = res.getPackDetailsId();
					Integer districtId = res.getDistrictId();
					// Step 2: minAmount
					BigDecimal minAmount = pricePerPackDetailsRepo.findMinAmount(packId.intValue(), districtId);
					if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) <= 0) {
						result.put("status", "Min amount not found");
					}
					// âœ… Wallet Update Logic
					BigDecimal paidAmount = new BigDecimal(amount); // get real amount dynamically

					// Optional<Wallet> existingWallet =
					// walletRepository.findByCustomerId(cusId.longValue());

					if (existingWallet.isPresent()) {

						Wallet wallet = existingWallet.orElseThrow(() -> 
					    new RuntimeException("Wallet not found for customer: " + cusId)
					);

						BigDecimal updatedAmount = wallet.getAmount().add(paidAmount);
						wallet.setAmount(updatedAmount);
						wallet.setLastPaymentDate(LocalDateTime.now());
						wallet.setLastpaidAmount(paidAmount);

						walletRepository.save(wallet);
						walletBalance = updatedAmount;
					} else {
						Wallet newWallet = new Wallet();
						newWallet.setCustomerId(cusId.longValue());
						newWallet.setAmount(paidAmount);
						newWallet.setLastPaymentDate(LocalDateTime.now());
						newWallet.setLastpaidAmount(paidAmount);
						walletRepository.save(newWallet);
						walletBalance = paidAmount;
					}

					// Step 6: Days calculate
					BigDecimal[] division = walletBalance.divideAndRemainder(minAmount);
					int days = division[0].intValue();
					BigDecimal remainingAmount = division[1];

					System.out.println(days);

					// Step 8: Sunday skip -> renewal date
					LocalDate startDate;

					if (currentRenewDate != null) {

						startDate = currentRenewDate;
					} else {
						startDate = LocalDate.now();
					}
					System.out.println(startDate);
					LocalDate renewalLocalDate = calculateRenewalFromDays(startDate, days);
					LocalDateTime nextRenewDate = renewalLocalDate.atStartOfDay();
					System.out.println(nextRenewDate);
					int customerStatus = 5;
					int customized = 0;
					customerDetailsRepo.updatePaymentStatus(true, nextRenewDate, cusId, orderId, customerStatus,
							LocalDateTime.now(), customized);
					
					result.put("status", "Payment Verified");

				}

//				if (isRenewed == true || isFromWallet) {
//					
//				}

				else {
					// LocalDateTime nextRenewDate = LocalDateTime.now().plusDays(30);

					// âœ… Calculate next renewal using 26 delivery days (excluding Sundays)
					LocalDateTime today = LocalDateTime.now();
					DayOfWeek day = today.getDayOfWeek();
					LocalDateTime nextRenewalLocalDate;
					LocalDateTime startDate;
					if (day == DayOfWeek.FRIDAY) {
						startDate = today.plusDays(3);
						nextRenewalLocalDate = calculateNextRenewalDate(startDate);
					} else {
						startDate = today.plusDays(2);
						nextRenewalLocalDate = calculateNextRenewalDate(startDate);
					}

					// ✅ Save startDate in DB
					customerDetailsRepo.updateStartDate(cusId.longValue(), startDate.toLocalDate());

					// âœ… Convert to LocalDateTime (start of day)
					LocalDateTime nextRenewDate = nextRenewalLocalDate;

					int customerStatus = 5;
					int customized = 0;
					customerDetailsRepo.updatePaymentStatus(true, nextRenewDate, cusId, orderId, customerStatus,
							LocalDateTime.now(), customized);

					result.put("status", "Payment Verified");
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

	public LocalDateTime calculateNextRenewalDate(LocalDateTime startDate) {

		int deliveryDays = 0;
		LocalDateTime renewalDate = startDate;

		while (deliveryDays < 26) {
			renewalDate = renewalDate.plusDays(1);

			// Skip Sundays
			if (renewalDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
				deliveryDays++;
			}
		}
		return renewalDate;
	}
}
