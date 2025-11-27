package com.destore.financegateway.controller;

import com.destore.financegateway.application.FinanceGatewayService;
import com.destore.financegateway.domain.FinanceDecision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
public class FinanceController {

    private final FinanceGatewayService service;

    public FinanceController(FinanceGatewayService service) {
        this.service = service;
    }

    @PostMapping("/approve")
    public ResponseEntity<FinanceDecision> approve(@Valid @RequestBody FinanceRequest body) {
        FinanceDecision decision = service.requestApproval(body.customerId(), body.amount());
        return ResponseEntity.ok(decision);
    }

    public record FinanceRequest(@NotBlank String customerId, @NotNull @Positive Double amount) { }
}
