package com.example.demo.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AvailablePromoCode;
import com.example.demo.entity.CustomerDetails;
import com.example.demo.entity.PaymentDetails;
import com.example.demo.entity.Wallet;
import com.example.demo.projection.CustomerPackDistrictProjection;
import com.example.demo.projection.StagingRenewalProjection;
import com.example.demo.repo.AvailablePromoCodeRepo;
import com.example.demo.repo.CustomerDetailsRepo;
import com.example.demo.repo.PaymentDetailsrepo;
import com.example.demo.repo.PricePerPackDetailsRepository;
import com.example.demo.repo.StagingRenewalRepo;
import com.example.demo.repo.WalletRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class PaymentService {
	@Autowired
	private PaymentDetailsrepo paymentDetailsrepo;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	@Autowired
	private AvailablePromoCodeRepo availablePromoCodeRepo;

	@Autowired
	private PricePerPackDetailsRepository pricePerPackDetailsRepo;
	
	@Autowired
    private StagingRenewalRepo stagingRepo;
	
	private final RazorpayClient client;
	private final String keySecret;
	private final String keyId;

	public PaymentService(@Value("${razorpay.key_id}") String keyId, @Value("${razorpay.key_secret}") String keySecret)
			throws Exception {
		this.client = new RazorpayClient(keyId, keySecret);
		this.keySecret = keySecret;
		this.keyId = keyId;
	}

	public Map<String, Object> createOrder(int amount, String currency, String receipt, long customerId)
			throws Exception {
		JSONObject options = new JSONObject();
		options.put("amount", amount * 100); // convert ₹ to paise
		options.put("currency", currency);
		options.put("receipt", receipt);
		options.put("payment_capture", 1);

		Order order = client.orders.create(options);
		String orderId = order.get("id").toString();
		Optional<CustomerDetails> cust = customerDetailsRepo.findById(customerId);

		Map<String, Object> response = new HashMap<>();
		response.put("orderId", orderId);
		response.put("amount", amount);
		response.put("currency", currency);
		response.put("receipt", receipt);
		response.put("key", keyId);
		response.put("phoneNumber", cust.get().getPhoneNumber());
		response.put("customerName", cust.get().getName());
		response.put("mailId", cust.get().getMailId());

		return response;
	}

	public boolean verifySignature(String orderId, String paymentId, String signature) {
		try {
			String data = orderId + "|" + paymentId;
			return Utils.verifySignature(data, signature, keySecret);

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public List<Map<String, Object>> processPayment(String orderId, String paymentId, String signature,
			List<Integer> customerIds, String promoCode, Long amount, Boolean isRenewed, Boolean isFromWallet) {

		boolean isValid = verifySignature(orderId, paymentId, signature);

		List<Map<String, Object>> results = new ArrayList<>();

		for (Integer customerId : customerIds) {

			Map<String, Object> result = new HashMap<>();

			result.put("customerId", customerId);
			result.put("orderId", orderId);

			try {

				savePaymentRecord(customerId, orderId, paymentId, isValid, amount);

				if (!isValid) {

					result.put("status", "Payment Verification Failed");

					results.add(result);
					continue;
				}

				processPromoCode(promoCode);

				if (Boolean.TRUE.equals(isFromWallet)) {

					processWalletPayment(customerId, amount, orderId);

				} else {

					processSubscriptionPayment(customerId, orderId, isRenewed);
				}

				result.put("status", "Payment Verified");

			} catch (Exception ex) {

				result.put("status", ex.getMessage());
			}

			results.add(result);
		}

		return results;
	}

	private void savePaymentRecord(Integer customerId, String orderId, String paymentId, boolean success, Long amount) {

		PaymentDetails payment = new PaymentDetails();

		payment.setOrderId(orderId);
		payment.setCusId(customerId);
		payment.setPaymentId(paymentId);
		payment.setStatusId(1L);
		payment.setCreatedBy("User");
		payment.setAmount(amount);
		payment.setCreatedTime(LocalDateTime.now());

		payment.setStatus(success ? "SUCCESS" : "FAILED");

		paymentDetailsrepo.save(payment);
	}

	private void processPromoCode(String promoCode) {

		if (promoCode == null) {
			return;
		}

		Optional<AvailablePromoCode> promo = availablePromoCodeRepo.findValidPromo(promoCode);

		if (promo.isPresent() && promo.get().getDiscountPercentage() == 100) {

			availablePromoCodeRepo.markPromoAsUsed(promoCode);
		}
	}

	private void processWalletPayment(Integer customerId, Long amount, String orderId) {

		// Get Pack & District
		CustomerPackDistrictProjection customerInfo = customerDetailsRepo
				.findPackAndDistrictByCustomerId(customerId.longValue());

		if (customerInfo == null) {
			throw new RuntimeException("Customer not found: " + customerId);
		}

		// Current Renewal Date
		LocalDate currentRenewDate = customerDetailsRepo.findNextRenewalDateByCustomerId(customerId);

		// Get Min Amount
		BigDecimal minAmount = pricePerPackDetailsRepo.findMinAmount(customerInfo.getPackDetailsId(),
				customerInfo.getDistrictId());

		if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) <= 0) {

			throw new RuntimeException("Min Amount not configured");
		}

		BigDecimal paidAmount = BigDecimal.valueOf(amount);

		// Find Wallet
		Wallet wallet = walletRepository.findByCustomerId(customerId.longValue()).orElseGet(() -> {

			Wallet newWallet = new Wallet();
			newWallet.setCustomerId(customerId.longValue());
			newWallet.setAmount(BigDecimal.ZERO);

			return newWallet;
		});

		// Update Wallet Balance
		BigDecimal updatedWalletBalance = wallet.getAmount().add(paidAmount);

		wallet.setAmount(updatedWalletBalance);
		wallet.setLastPaymentDate(LocalDateTime.now());
		wallet.setLastpaidAmount(paidAmount);

		walletRepository.save(wallet);

		// Calculate Delivery Days
		BigDecimal[] division = updatedWalletBalance.divideAndRemainder(minAmount);

		int deliveryDays = division[0].intValue();

		// Start Date
		LocalDate startDate = currentRenewDate != null ? currentRenewDate : LocalDate.now();

		// Renewal Date
		LocalDate renewalDate = calculateRenewalFromDays(startDate, deliveryDays);

		LocalDateTime nextRenewDate = renewalDate.atStartOfDay();

		// Update Customer
		int customerStatus = 5;
		int customized = 0;

		customerDetailsRepo.updatePaymentStatus(true, nextRenewDate, customerId, orderId, customerStatus,
				LocalDateTime.now(), customized);

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

	private void processSubscriptionPayment(Integer customerId, String orderId, Boolean isRenewed) {

// Get customer rate
		BigDecimal walletAmountToAdd =
		        Optional.ofNullable(
		                getWalletAmountForPayment(
		                        customerId.longValue(),
		                        isRenewed))
		        .orElseThrow(() ->
		                new RuntimeException(
		                        "Rate not found"));
// Wallet Upsert
		Wallet wallet = walletRepository.findByCustomerId(customerId.longValue()).orElseGet(() -> {

			Wallet newWallet = new Wallet();
			newWallet.setCustomerId(customerId.longValue());
			newWallet.setAmount(BigDecimal.ZERO);

			return newWallet;
		});

// Update Wallet
		BigDecimal updatedAmount = wallet.getAmount().add(walletAmountToAdd);

		wallet.setAmount(updatedAmount);
		wallet.setLastPaymentDate(LocalDateTime.now());
		wallet.setLastpaidAmount(walletAmountToAdd);

		walletRepository.save(wallet);

		if (Boolean.TRUE.equals(isRenewed)) {
			// renewal logic
		} else {
			LocalDateTime today = LocalDateTime.now();

			LocalDateTime startDate;

			if (today.getDayOfWeek() == DayOfWeek.FRIDAY) {

				startDate = today.plusDays(3);

			} else {

				startDate = today.plusDays(2);
			}

			// Calculate next renewal date
			LocalDateTime nextRenewDate = calculateNextRenewalDate(startDate);

			// Save start date
			customerDetailsRepo.updateStartDate(customerId.longValue(), startDate.toLocalDate());

			int customerStatus = 5;
			int customized = 0;

			customerDetailsRepo.updatePaymentStatus(true, nextRenewDate, customerId, orderId, customerStatus,
					LocalDateTime.now(), customized);
		}
	}
	
	private BigDecimal getWalletAmountForPayment(
        Long customerId,
        Boolean isRenewed) {

    if (!Boolean.TRUE.equals(isRenewed)) {
        return customerDetailsRepo.findRateByCustomerId(customerId);
    }

    BigDecimal renewalRate =
    		stagingRepo.findRenewalPackRate(customerId);

    if (renewalRate != null) {
        return renewalRate;
    }

    return customerDetailsRepo.findRateByCustomerId(customerId);
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
