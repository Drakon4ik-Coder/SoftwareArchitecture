package com.destore.pricing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    private String sku;
    private String currency;
    private BigDecimal amount;

    protected Price() {
        // JPA only
    }

    public Price(String sku, String currency, BigDecimal amount) {
        this.sku = sku;
        this.currency = currency;
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
