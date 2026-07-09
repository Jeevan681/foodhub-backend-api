package com.jeevan.foodhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    private String imageUrl;

    private Boolean available;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToMany(
            mappedBy = "food",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(
            mappedBy = "food",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Favorite> favorites = new ArrayList<>();
}