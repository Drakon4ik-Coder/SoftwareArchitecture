package com.destore.contracts.events;

/**
 * Example event raised when inventory drops below a threshold.
 */
public record LowStockEvent(String sku, int remainingQuantity, String storeId) {
}
