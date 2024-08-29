package com.manoj.autonest.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.manoj.autonest.dto.UserDto;
import com.manoj.autonest.model.User;
import com.manoj.autonest.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    
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

    
    
    
    
    
    // Dealer Routes
    @GetMapping("/dealer-page")
    public String dealerPage() {
    	return "dealer";
    }
    
    
    
    // User Routes
    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
    	UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
    	model.addAttribute("user", userDetails);
    	return "user";
    }
}
