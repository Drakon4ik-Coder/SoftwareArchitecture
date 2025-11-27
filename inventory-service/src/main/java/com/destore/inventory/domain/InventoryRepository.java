package com.destore.inventory.domain;

import java.util.Optional;

public interface InventoryRepository {
    InventoryItem save(InventoryItem item);
    Optional<InventoryItem> findBySku(String sku);
}
