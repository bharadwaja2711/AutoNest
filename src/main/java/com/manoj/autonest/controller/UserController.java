package com.manoj.autonest.controller;

import java.nio.file.Files;

import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.autonest.dto.UserDto;
import com.manoj.autonest.model.Car;
import com.manoj.autonest.model.CustomerOrder;
import com.manoj.autonest.model.User;
import com.manoj.autonest.repositories.OrderRepository;
import com.manoj.autonest.repositories.UserRepository;
import com.manoj.autonest.service.UserService;

import jakarta.persistence.criteria.Path;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    // Index Page Route
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    // Registration page routes
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
    	userService.save(userDto);
    	model.addAttribute("message", "Registered Successfully!");
    	return "register";
    }
    
    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    // Login Route
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    
    // *******Admin Routes************
    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }
    
    // User Management
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/admin-page/usermanagement")
    public String userManagement() {
        return "usermanagement";
    }
    
    @PatchMapping("/api/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> roleData) {
        String newRole = roleData.get("role");
        User updatedUser = userService.updateUserRole(id, newRole);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }
    }
    
    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Fetching the email of the logged-in user from the UserDetails object
        String email = userDetails.getUsername();

        // Retrieving the user by email
        User user = userService.findByEmail(email);
        
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "profile"; // This corresponds to the profile.html template
    }
    
    @GetMapping("/dealerprofile")
    public String usersProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Fetching the email of the logged-in user from the UserDetails object
        String email = userDetails.getUsername();

        // Retrieving the user by email
        User user = userService.findByEmail(email);
        
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "dealerprofile"; // This corresponds to the profile.html template
    }
    
    @GetMapping("/admin-page/vehiclemanagement")
    public String vehicleManagement() {
        return "vehiclemanagement";
    }
    
    @GetMapping("/admin-page/orders")
    public String orders(Model model) {
        List<CustomerOrder> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    
    
    
    // **************Dealer Routes***************
    @GetMapping("/dealer-page")
    public String dealerPage() {
    	return "dealer";
    }
    
    @GetMapping("/dealer-page/orders")
    public String order(Model model) {
        List<CustomerOrder> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "dealerorders";
    }
    
    
    
    //**************User Routes******************
    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
    	UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
    	model.addAttribute("user", userDetails);
    	return "user";
    }
    
    //*************Global routes*********************
    @GetMapping("/api/user-details")
    public ResponseEntity<?> getUserDetails(Principal principal) {
        // Assuming the principal's name is the user's email
        String userEmail = principal.getName(); 
        User user = userRepository.findByEmail(userEmail);

        if (user != null) {
            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("email", user.getEmail());
            userDetails.put("mobno", user.getMobno());

            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
    @PatchMapping("/profile/update")
    public ResponseEntity<Map<String, String>> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestBody Map<String, String> updates) {
        // Get the email of the logged-in user
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "failure", "message", "User not found"));
        }

        // Update the user's profile based on the fields provided in the request body
        updates.forEach((field, value) -> {
            switch (field) {
                case "email":
                    user.setEmail(value);
                    break;
                case "mobno":
                    user.setMobno(value);
                    break;
                case "gender":
                    user.setGender(value);
                    break;
                case "city":
                    user.setCity(value);
                    break;
                case "state":
                    user.setState(value);
                    break;
                case "country":
                    user.setCountry(value);
                    break;
                case "pincode":
                    user.setPincode(value);
                    break;
                default:
                    break;
            }
        });

        // Save the updated user
        userService.save(user);

        // Return success response
        return ResponseEntity.ok(Map.of("status", "success", "message", "Profile updated successfully"));
    }
    
    
}