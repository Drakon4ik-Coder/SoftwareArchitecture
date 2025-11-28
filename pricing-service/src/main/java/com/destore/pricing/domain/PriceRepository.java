package com.destore.pricing.domain;

import java.util.Optional;
import java.util.List;

public interface PriceRepository {
    Price save(Price price);
    Optional<Price> findBySku(String sku);
    List<Price> findAll();
}
