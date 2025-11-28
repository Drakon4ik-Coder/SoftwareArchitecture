package com.destore.pricing.controller;

import com.destore.pricing.application.PricingApplicationService;
import com.destore.pricing.domain.Price;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/pricing")
public class PricingController {

    private final PricingApplicationService appService;

    public PricingController(PricingApplicationService appService) {
        this.appService = appService;
    }

    @PostMapping("/{sku}")
    public ResponseEntity<Price> setPrice(@PathVariable String sku, @Valid @RequestBody SetPriceRequest request) {
        Price saved = appService.setPrice(sku, request.currency(), request.amount(), request.offerType());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Price> getPrice(@PathVariable String sku) {
        Optional<Price> price = appService.findPrice(sku);
        return price.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Price>> list() {
        return ResponseEntity.ok(appService.list());
    }

    public record SetPriceRequest(
            @NotBlank String currency,
            @NotNull @Positive BigDecimal amount,
            @NotBlank String offerType
    ) { }
}
