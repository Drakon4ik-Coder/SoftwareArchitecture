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
    private String offerType;

    protected Price() {
        // JPA only
    }

    public Price(String sku, String currency, BigDecimal amount, String offerType) {
        this.sku = sku;
        this.currency = currency;
        this.amount = amount;
        this.offerType = offerType;
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

    public String getOfferType() {
        return offerType;
    }
}
