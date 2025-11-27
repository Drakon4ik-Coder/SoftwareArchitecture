# Pricing Service

Exposes price CRUD and emits `PriceChangedEvent` for downstream consumers.

## Run
```bash
mvn -pl pricing-service spring-boot:run -Dmaven.repo.local=.m2repo
```
Port: 8081 (see `application.yml`).

## API samples
```bash
# Set price
curl -X POST http://localhost:8081/pricing/sku123 \
  -H "Content-Type: application/json" \
  -d '{"currency":"GBP","amount":9.99}'

# Get price
curl http://localhost:8081/pricing/sku123
```

## Notes
- Uses H2 in-memory DB by default.
- Events currently go through Spring `ApplicationEventPublisher`; replace with a broker for real async.
- Extend validation and domain rules as needed. Cite AI assistance per coursework rules.
