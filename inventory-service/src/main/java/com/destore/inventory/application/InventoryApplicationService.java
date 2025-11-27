package com.destore.inventory.application;

import com.destore.contracts.events.LowStockEvent;
import com.destore.inventory.domain.InventoryItem;
import com.destore.inventory.domain.InventoryRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryApplicationService {

    private static final Logger log = LoggerFactory.getLogger(InventoryApplicationService.class);
    private static final int LOW_STOCK_THRESHOLD = 5;

    private final InventoryRepository repo;
    private final KafkaTemplate<String, LowStockEvent> kafkaTemplate;
    private final String lowStockTopic;

    public InventoryApplicationService(
            InventoryRepository repo,
            KafkaTemplate<String, LowStockEvent> kafkaTemplate,
            @Value("${destore.topics.low-stock}") String lowStockTopic) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
        this.lowStockTopic = lowStockTopic;
    }

    @Transactional
    public InventoryItem adjust(String sku, int delta, String storeId) {
        InventoryItem item = repo.findBySku(sku).orElse(new InventoryItem(sku, 0));
        item.adjust(delta);
        InventoryItem saved = repo.save(item);

        if (saved.getQuantity() <= LOW_STOCK_THRESHOLD) {
            LowStockEvent event = new LowStockEvent(sku, saved.getQuantity(), storeId);
            kafkaTemplate.send(new ProducerRecord<>(lowStockTopic, sku, event));
            log.info("Low stock for sku={} qty={}", sku, saved.getQuantity());
        }
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<InventoryItem> find(String sku) {
        return repo.findBySku(sku);
    }
}
