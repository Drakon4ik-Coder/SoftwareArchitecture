package com.destore.loyalty.controller;

import com.destore.loyalty.application.LoyaltyApplicationService;
import com.destore.loyalty.domain.LoyaltyMember;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {

    private final LoyaltyApplicationService service;

    public LoyaltyController(LoyaltyApplicationService service) {
        this.service = service;
    }

    @PostMapping("/members")
    public ResponseEntity<LoyaltyMember> register(@Valid @RequestBody RegisterRequest body) {
        LoyaltyMember member = service.register(body.name(), body.email(), body.tier());
        return ResponseEntity.ok(member);
    }

    @PostMapping("/members/{id}/points")
    public ResponseEntity<LoyaltyMember> addPoints(@PathVariable Long id, @Valid @RequestBody PointsRequest body) {
        Optional<LoyaltyMember> updated = service.addPoints(id, body.points());
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<LoyaltyMember> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/members")
    public ResponseEntity<List<LoyaltyMember>> list() {
        return ResponseEntity.ok(service.list());
    }

    public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String tier) { }

    public record PointsRequest(@NotNull @Positive Integer points) { }
}
