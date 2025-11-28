package com.destore.loyalty.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loyalty_members")
public class LoyaltyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String tier;
    private int points;

    protected LoyaltyMember() {
    }

    public LoyaltyMember(String name, String email, String tier, int points) {
        this.name = name;
        this.email = email;
        this.tier = tier;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTier() {
        return tier;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int delta) {
        this.points += delta;
    }

    public void updateTier(String tier) {
        this.tier = tier;
    }
}
