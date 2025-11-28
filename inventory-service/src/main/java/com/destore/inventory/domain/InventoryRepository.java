package com.destore.inventory.domain;

import java.util.Optional;
import java.util.List;

public interface InventoryRepository {
    InventoryItem save(InventoryItem item);
    Optional<InventoryItem> findBySku(String sku);
    List<InventoryItem> findAll();
}
