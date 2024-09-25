package com.manoj.autonest.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Add reference to the User entity

    @ManyToMany
    @JoinTable(
        name = "forum_likes",
        joinColumns = @JoinColumn(name = "forum_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void like(User user) {
        if (!likedBy.contains(user)) {
            likedBy.add(user);
        }
    }

    public void unlike(User user) {
        likedBy.remove(user);
    }

    public int getLikeCount() {
        return likedBy.size(); // Return the number of unique users who liked this forum
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }
}
