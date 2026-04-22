package com.example.demo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerDetails;
import com.example.demo.repo.CustomerDetailsRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentService {
	@Autowired
	private CustomerDetailsRepo customerRepo;
	
	private final RazorpayClient client;
	private final String keySecret;
	private final String keyId;

	public PaymentService(@Value("${razorpay.key_id}") String keyId, @Value("${razorpay.key_secret}") String keySecret)
			throws Exception {
		this.client = new RazorpayClient(keyId, keySecret);
		this.keySecret = keySecret;
		this.keyId = keyId;
	}

	public Map<String, Object> createOrder(int amount, String currency, String receipt, long customerId) throws Exception {
		JSONObject options = new JSONObject();
		options.put("amount", amount * 100); // convert ₹ to paise
		options.put("currency", currency);
		options.put("receipt", receipt);
		options.put("payment_capture", 1);

		Order order = client.orders.create(options);
		String orderId = order.get("id").toString();
		 Optional<CustomerDetails> cust = customerRepo.findById(customerId);
		 
		
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
}
