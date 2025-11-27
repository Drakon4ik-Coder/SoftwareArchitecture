package com.destore.financegateway.application;

import com.destore.financegateway.domain.FinanceDecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.UUID;

@Service
public class FinanceGatewayService {

    private static final Logger log = LoggerFactory.getLogger(FinanceGatewayService.class);

    private final WebClient enablingClient;

    public FinanceGatewayService(WebClient.Builder builder) {
        // Point this to the real Enabling endpoint when available.
        this.enablingClient = builder.baseUrl("http://localhost:9090/enabling").build();
    }

    public FinanceDecision requestApproval(String customerId, double amount) {
        // TODO: replace with real call to Enabling API. This stub simulates latency and approval logic.
        simulateRemoteCall();
        String status = amount <= 1000 ? "APPROVED" : "REFERRED";
        String reason = status.equals("APPROVED") ? "Auto-approval under threshold" : "Requires manual review";
        FinanceDecision decision = new FinanceDecision(UUID.randomUUID().toString(), status, reason);
        log.info("Finance decision for customer={} amount={} status={}", customerId, amount, status);
        return decision;
    }

    private void simulateRemoteCall() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
