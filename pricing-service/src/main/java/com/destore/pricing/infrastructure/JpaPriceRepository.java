package com.destore.pricing.infrastructure;

import com.destore.pricing.domain.Price;
import com.destore.pricing.domain.PriceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface SpringDataPriceRepository extends JpaRepository<Price, String> {
    Optional<Price> findBySku(String sku);
}

@Repository
public class JpaPriceRepository implements PriceRepository {

    private final SpringDataPriceRepository delegate;

    public JpaPriceRepository(SpringDataPriceRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Price save(Price price) {
        return delegate.save(price);
    }

    @Override
    public Optional<Price> findBySku(String sku) {
        return delegate.findBySku(sku);
    }
}
