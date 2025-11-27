package com.destore.pricing.application;

import com.destore.contracts.events.PriceChangedEvent;
import com.destore.contracts.events.PriceChangedEvent;
import com.destore.pricing.domain.Price;
import com.destore.pricing.domain.PriceRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PricingApplicationService {

    private static final Logger log = LoggerFactory.getLogger(PricingApplicationService.class);

    private final PriceRepository repo;
    private final KafkaTemplate<String, PriceChangedEvent> kafkaTemplate;
    private final String priceChangedTopic;

    public PricingApplicationService(
            PriceRepository repo,
            KafkaTemplate<String, PriceChangedEvent> kafkaTemplate,
            @Value("${destore.topics.price-changed}") String priceChangedTopic) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
        this.priceChangedTopic = priceChangedTopic;
    }

    @Transactional
    public Price setPrice(String sku, String currency, BigDecimal amount) {
        Price price = new Price(sku, currency, amount);
        Price saved = repo.save(price);
        PriceChangedEvent event = new PriceChangedEvent(sku, currency, amount);
        kafkaTemplate.send(new ProducerRecord<>(priceChangedTopic, sku, event));
        log.info("Price set for sku={} amount={}", sku, amount);
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<Price> findPrice(String sku) {
        return repo.findBySku(sku);
    }
}
