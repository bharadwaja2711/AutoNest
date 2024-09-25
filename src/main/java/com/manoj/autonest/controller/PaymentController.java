package com.manoj.autonest.controller;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import org.json.JSONObject; // Import JSONObject
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private static final String RAZORPAY_KEY_ID = "rzp_test_cmrkOmlYz5q6KG";
    private static final String RAZORPAY_SECRET = "dOsi9XHlkHuXp7NTpMFRXH4K";

    @PostMapping("/create-order/{carId}")
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable String carId) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SECRET);
            JSONObject options = new JSONObject(); // Create a JSONObject
            options.put("amount", 1000); // Amount in paise (i.e., ₹100)
            options.put("currency", "INR");
            options.put("receipt", carId);
            options.put("payment_capture", 1); // Auto capture
            Order order = razorpayClient.orders.create(options); // Pass JSONObject

            Map<String, Object> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
