package com.destore.reporting.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ReportingService {

    private final WebClient webClient;
    private final String pricingBaseUrl;
    private final String inventoryBaseUrl;

    public ReportingService(WebClient.Builder builder,
                            @Value("${services.pricing.base-url}") String pricingBaseUrl,
                            @Value("${services.inventory.base-url}") String inventoryBaseUrl) {
        this.webClient = builder.build();
        this.pricingBaseUrl = pricingBaseUrl;
        this.inventoryBaseUrl = inventoryBaseUrl;
    }

    public Map<String, Object> summary() {
        List<PriceDto> prices = fetchPrices();
        List<InventoryDto> inventory = fetchInventory();
        long lowStock = inventory.stream().filter(i -> i.quantity <= 5).count();
        long activePromos = prices.stream().filter(p -> !p.offerType.equalsIgnoreCase("Standard")).count();

        return Map.of(
                "generatedAt", Instant.now().toString(),
                "pricingCount", prices.size(),
                "inventorySkus", inventory.size(),
                "lowStockSkus", lowStock,
                "activePromotions", activePromos,
                "note", "Aggregated via service APIs"
        );
    }

    private List<PriceDto> fetchPrices() {
        return webClient.get()
                .uri(pricingBaseUrl + "/pricing")
                .retrieve()
                .bodyToFlux(PriceDto.class)
                .collectList()
                .onErrorReturn(List.of())
                .block();
    }

    private List<InventoryDto> fetchInventory() {
        return webClient.get()
                .uri(inventoryBaseUrl + "/inventory")
                .retrieve()
                .bodyToFlux(InventoryDto.class)
                .collectList()
                .onErrorReturn(List.of())
                .block();
    }

    private record PriceDto(String sku, String currency, double amount, String offerType) { }
    private record InventoryDto(String sku, int quantity) { }
}
