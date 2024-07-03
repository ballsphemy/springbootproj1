package com.aclc.InventoryManagementSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;
    private LocalDateTime timestamp;

    // Constructors, getters and setters

    public Logs() {}

    public Logs(User user, String action) {
        this.user = user;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    // other getters and setters
}