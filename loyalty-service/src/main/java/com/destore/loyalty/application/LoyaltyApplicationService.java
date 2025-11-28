package com.destore.loyalty.application;

import com.destore.loyalty.domain.LoyaltyMember;
import com.destore.loyalty.domain.LoyaltyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoyaltyApplicationService {

    private final LoyaltyMemberRepository repo;

    public LoyaltyApplicationService(LoyaltyMemberRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public LoyaltyMember register(String name, String email, String tier) {
        LoyaltyMember member = new LoyaltyMember(name, email, tier, 0);
        return repo.save(member);
    }

    @Transactional
    public Optional<LoyaltyMember> addPoints(Long memberId, int points) {
        return repo.findById(memberId).map(member -> {
            member.addPoints(points);
            return member;
        });
    }

    @Transactional(readOnly = true)
    public Optional<LoyaltyMember> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LoyaltyMember> list() {
        return repo.findAll();
    }
}
