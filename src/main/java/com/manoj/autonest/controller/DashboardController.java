package com.manoj.autonest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.manoj.autonest.service.CustomUserDetail; // Adjust the package as necessary

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/api/active-users")
    public Map<String, String> getActiveUsers() {
        Map<String, String> activeUsers = new HashMap<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();

        System.out.println("Active principals count: " + principals.size());

        for (Object principal : principals) {
            if (principal instanceof CustomUserDetail) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                String username = userDetails.getGivenname(); // Adjust as necessary
                System.out.println("Active user: " + username);
                activeUsers.put(username, "Active Now");
            }
        }

        return activeUsers;
    }

}
