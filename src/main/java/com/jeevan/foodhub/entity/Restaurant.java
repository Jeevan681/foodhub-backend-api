package com.jeevan.foodhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private String address;
    private String phone;
    private String imageUrl;

    @Builder.Default
    private Boolean isOpen = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Food> foods = new ArrayList<>();
    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();
}