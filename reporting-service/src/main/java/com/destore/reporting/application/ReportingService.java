package com.destore.reporting.application;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class ReportingService {

    public Map<String, Object> summary() {
        // Prototype stub: replace with real aggregation of sales/inventory/loyalty in future.
        return Map.of(
                "generatedAt", Instant.now().toString(),
                "salesTotal", 0,
                "activePromotions", 0,
                "lowStockAlerts", 0,
                "note", "Prototype stub - connect to accounting DB or event stream in future"
        );
    }
}
