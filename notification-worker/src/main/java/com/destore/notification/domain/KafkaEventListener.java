package com.destore.notification.domain;

import com.destore.contracts.events.LowStockEvent;
import com.destore.contracts.events.PriceChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Prototype Kafka consumer. Replace logging with real notification channels.
 */
@Component
public class KafkaEventListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventListener.class);

    @KafkaListener(topics = "${destore.topics.low-stock}", groupId = "destore-notification")
    public void onLowStock(LowStockEvent event) {
        log.info("Notify manager: SKU {} low stock (qty={}) at store {}", event.sku(), event.remainingQuantity(), event.storeId());
    }

    @KafkaListener(topics = "${destore.topics.price-changed}", groupId = "destore-notification")
    public void onPriceChanged(PriceChangedEvent event) {
        log.info("Notify caches/reporting: price changed for {} {} {} offer={}", event.sku(), event.currency(), event.amount(), event.offerType());
    }
}
