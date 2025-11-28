package com.destore.loyalty.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoyaltyMemberRepository extends JpaRepository<LoyaltyMember, Long> {
    Optional<LoyaltyMember> findByEmail(String email);
}
