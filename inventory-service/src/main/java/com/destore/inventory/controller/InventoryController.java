package com.destore.inventory.controller;

import com.destore.inventory.application.InventoryApplicationService;
import com.destore.inventory.domain.InventoryItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryApplicationService appService;

    public InventoryController(InventoryApplicationService appService) {
        this.appService = appService;
    }

    @PostMapping("/{sku}/adjust")
    public ResponseEntity<InventoryItem> adjust(@PathVariable String sku, @Valid @RequestBody AdjustStockRequest body) {
        InventoryItem item = appService.adjust(sku, body.delta(), body.storeId());
        return ResponseEntity.ok(item);
    }

    @PostMapping("/upload")
    public ResponseEntity<InventoryItem> upload(@Valid @RequestBody UploadStockRequest body) {
        InventoryItem item = appService.upload(body.sku(), body.quantity(), body.storeId());
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryItem> get(@PathVariable String sku) {
        Optional<InventoryItem> item = appService.find(sku);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> list() {
        return ResponseEntity.ok(appService.list());
    }

    public record AdjustStockRequest(@NotNull Integer delta, @NotBlank String storeId) { }

    public record UploadStockRequest(@NotBlank String sku, @NotNull Integer quantity, @NotBlank String storeId) { }
}
