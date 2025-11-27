package com.destore.inventory.infrastructure;

import com.destore.inventory.domain.InventoryItem;
import com.destore.inventory.domain.InventoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataInventoryRepo extends JpaRepository<InventoryItem, String> {
    Optional<InventoryItem> findBySku(String sku);
}

@Repository
public class JpaInventoryRepository implements InventoryRepository {
    private final SpringDataInventoryRepo delegate;

    public JpaInventoryRepository(SpringDataInventoryRepo delegate) {
        this.delegate = delegate;
    }

    @Override
    public InventoryItem save(InventoryItem item) {
        return delegate.save(item);
    }

    @Override
    public Optional<InventoryItem> findBySku(String sku) {
        return delegate.findBySku(sku);
    }
}
