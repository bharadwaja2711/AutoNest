package com.manoj.autonest.controller;

import com.razorpay.RazorpayClient;
import com.manoj.autonest.model.CustomerOrder;
import com.manoj.autonest.repositories.OrderRepository;
import com.razorpay.Order;
import org.json.JSONObject; // Import JSONObject
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {
	
	@Autowired
    private OrderRepository orderRepository;


    private static final String RAZORPAY_KEY_ID = "rzp_test_cmrkOmlYz5q6KG";
    private static final String RAZORPAY_SECRET = "dOsi9XHlkHuXp7NTpMFRXH4K";

    // Create Razorpay order
    @PostMapping("/create-order/{carId}")
    public ResponseEntity<Map<String, Object>> createOrder(@PathVariable String carId) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SECRET);
            JSONObject options = new JSONObject(); // Create a JSONObject
            options.put("amount", 100); // Amount in paise (i.e., ₹10)
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

    // Capture the success response from Razorpay
    @PostMapping("/payment-success")
    public ResponseEntity<String> handlePaymentSuccess(@RequestBody Map<String, Object> data) {
        try {
            // Extract the relevant data from Razorpay's callback
            String paymentId = (String) data.get("razorpay_payment_id");
            String orderId = (String) data.get("razorpay_order_id");
            String paymentStatus = "success"; // As this endpoint is for successful payments
            //String userName = (String) data.get("user_name"); // Assuming user_name is passed in the payload
            int amountPaid = (int) data.get("amount"); // Amount paid in paise

            // Log the payment details in the console
            System.out.println("Payment Status: " + paymentStatus);
            System.out.println("Payment ID: " + paymentId);
            System.out.println("Order ID: " + orderId);
            System.out.println("Amount Paid (in INR): ₹" + (amountPaid / 100.0)); // Convert paise to INR
            //System.out.println("Paid by: " + userName);

            // Return success message
            return ResponseEntity.ok("Payment logged successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment logging failed!");
        }
    }
    
    @PostMapping("/save-payment")
    public ResponseEntity<?> savePaymentDetails(@RequestBody Map<String, Object> paymentData) {
    	System.out.println("savePaymentDetails endpoint hit.");
        System.out.println("Received payment data: " + paymentData);
        try {
            // Log the received payment data
            System.out.println("Received payment data: " + paymentData);

            CustomerOrder order = new CustomerOrder();
            order.setPaymentId((String) paymentData.get("paymentId"));
            order.setAmount((Integer) paymentData.get("amount"));
            order.setCurrency((String) paymentData.get("currency"));
            order.setProductName((String) paymentData.get("productName"));
            order.setProductModel((String) paymentData.get("productModel"));
            order.setUserEmail((String) paymentData.get("userEmail"));
            order.setUserMobno((String) paymentData.get("userMobno"));

            orderRepository.save(order);  // Save to DB

            return ResponseEntity.ok("Payment details saved successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving payment details");
        }
    }

}

