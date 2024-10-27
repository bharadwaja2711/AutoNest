package com.manoj.autonest.controller;

import com.manoj.autonest.model.Forum;
import com.manoj.autonest.model.User;
import com.manoj.autonest.repositories.ForumRepository;
import com.manoj.autonest.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable; // Add this import

@Controller
public class ForumController {

    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    // Inject both ForumRepository and UserRepository
    @Autowired
    public ForumController(ForumRepository forumRepository, UserRepository userRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository; // Ensure UserRepository is injected
    }

    // Display the form for submitting a review
    @GetMapping("/admin-page/forummanagement")
    public String showForumForm(Model model) {
        List<Forum> forums = forumRepository.findAll(); // Fetch forums with user data
        model.addAttribute("forums", forums);  // Add the list of forums to the model
        return "viewforum";  // Return the forum view (viewforum.html)
    }
    
    @GetMapping("/dealer-page/forummanagement")
    public String showsForumForm(Model model) { 
        List<Forum> forums = forumRepository.findAll(); // Fetch forums with user data
        model.addAttribute("forums", forums);  // Add the list of forums to the model
        return "dealerviewforum";  // Return the forum view (viewforum.html)
    }

    // Display the create forum form
    @GetMapping("/createforum")
    public String createForumForm(Model model) {
        model.addAttribute("forum", new Forum());  // Ensure 'Forum' is your correct model class
        return "createforum"; // Return the create forum form view (createforum.html)
    }

    // Handle the form submission and save the forum with the logged-in user
    @PostMapping("/createforum")
    public String createForum(@ModelAttribute Forum forum, Principal principal) {
        // Fetch the logged-in user by their email or username
        User loggedInUser = userRepository.findByEmail(principal.getName());
        forum.setUser(loggedInUser); // Set the user who posted the review
        forumRepository.save(forum); // Save the forum entry to the database
        return "redirect:/admin-page/forummanagement";
    }

    


}
