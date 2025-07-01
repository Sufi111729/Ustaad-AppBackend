package com.sufi.UstaadAppBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String description;
    private String services;
    private String website;
    private String imageUrl;

    private Double latitude;
    private Double longitude;

    private double avgRating = 0.0;
    private int totalReviews = 0;

    private LocalDateTime createdAt = LocalDateTime.now();
}
