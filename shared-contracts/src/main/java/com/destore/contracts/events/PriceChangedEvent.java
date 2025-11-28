package com.destore.contracts.events;

import java.math.BigDecimal;

/**
 * Example domain event published by pricing-service when a price is updated.
 * Extend or replace fields as needed for your prototype.
 */
public record PriceChangedEvent(String sku, String currency, BigDecimal amount, String offerType) {
}
