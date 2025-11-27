# Inventory Service

Manages stock levels and emits `LowStockEvent` when quantities fall below a threshold.

## Run
```bash
mvn -pl inventory-service spring-boot:run -Dmaven.repo.local=.m2repo
```
Port: 8082 (see `application.yml`).

## API samples
```bash
# Adjust stock (negative to decrement)
curl -X POST http://localhost:8082/inventory/sku123/adjust \
  -H "Content-Type: application/json" \
  -d '{"delta":-10,"storeId":"store-1"}'

# Get stock
curl http://localhost:8082/inventory/sku123
```

## Notes
- H2 in-memory DB by default.
- Low-stock threshold is hardcoded; adjust in `InventoryApplicationService`.
- Events are in-process; replace with a broker for true decoupling. Cite AI assistance per coursework rules.
