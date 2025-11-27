# Notification Worker

Consumes domain events and logs notifications (prototype). Replace logging with email/SMS/push as needed.

## Run
```bash
mvn -pl notification-worker spring-boot:run -Dmaven.repo.local=.m2repo
```
Port: 8084 (for a simple `/notify/ping`).

## Behavior
- Listens for `LowStockEvent` and `PriceChangedEvent` using Spring `@EventListener`.
- Currently in-process; swap to a real message broker for cross-service delivery.

Adapt and extend as needed; cite AI assistance per coursework rules.
