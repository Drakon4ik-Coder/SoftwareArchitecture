package com.destore.pricing.domain;

import java.util.Optional;

public interface PriceRepository {
    Price save(Price price);
    Optional<Price> findBySku(String sku);
}
