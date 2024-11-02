package com.manoj.autonest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.manoj.autonest.model.CustomerOrder;
import com.manoj.autonest.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository; // Inject your repository

    @GetMapping("/user-page/orders")
    public String viewOrders(Model model) {
        // Get the logged-in user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName(); // This assumes the email is the username

        // Fetch all orders
        List<CustomerOrder> allOrders = orderRepository.findAll();

        // Filter orders by logged-in user's email
        List<CustomerOrder> userOrders = allOrders.stream()
                .filter(order -> order.getUserEmail().equals(loggedInUserEmail))
                .collect(Collectors.toList());

        model.addAttribute("orders", userOrders);
        return "userorders"; // Return the name of the HTML template
    }
}
