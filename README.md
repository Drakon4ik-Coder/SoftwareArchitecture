# DE-Store Prototype (Service-Based / Microservice-Lite)

Minimal Java/Spring Boot prototype to showcase the chosen architecture: API Gateway + domain services (pricing, inventory with events, finance gateway) and a notification worker. Each service owns its data and is reachable via the gateway.

## Modules
- `gateway` — Spring Cloud Gateway routes external calls to services.
- `pricing-service` — Set/get prices; emits `PriceChangedEvent`.
- `inventory-service` — Adjust stock; emits `LowStockEvent` when below threshold.
- `finance-gateway-service` — Stub adapter to Enabling finance (approve/refer).
- `notification-worker` — Consumes events (log-only prototype).
- `shared-contracts` — DTOs/events shared across services.

## Prereqs
- JDK 21 (or 17) installed (`javac -version`).
- Maven 3.8+.
- Docker/Compose to start Kafka + Postgres (`docker compose up -d`).
- Network access for first dependency download.

## Build (uses local repo inside workspace)
```bash
mvn clean install -DskipTests -Dmaven.repo.local=.m2repo
```

## Build & run with Docker
- Build images: `docker compose build`
- Run stack (Kafka, Postgres, services, gateway): `docker compose up -d`
- Services use Kafka at `kafka:9092` and Postgres at `postgres:5432` (default creds `destore/destore`); ports exposed: gateway 8080, pricing 8081, inventory 8082, finance 8083.

## Run services (one terminal each)
Start infra: `docker compose up -d` (brings up Kafka/Zookeeper/Postgres). Then run:
```bash
KAFKA_BOOTSTRAP_SERVERS=localhost:9092 mvn -pl pricing-service spring-boot:run -Dmaven.repo.local=.m2repo
KAFKA_BOOTSTRAP_SERVERS=localhost:9092 mvn -pl inventory-service spring-boot:run -Dmaven.repo.local=.m2repo
KAFKA_BOOTSTRAP_SERVERS=localhost:9092 mvn -pl finance-gateway-service spring-boot:run -Dmaven.repo.local=.m2repo
KAFKA_BOOTSTRAP_SERVERS=localhost:9092 mvn -pl notification-worker spring-boot:run -Dmaven.repo.local=.m2repo
mvn -pl gateway spring-boot:run -Dmaven.repo.local=.m2repo
```
In Docker mode, the gateway routes are wired to service names (`pricing-service:8081`, `inventory-service:8082`, `finance-gateway-service:8083`, `notification-worker:8084`), so no localhost connections are used between containers.

Ports: gateway 8080, pricing 8081, inventory 8082, finance 8083, notification 8084 (see `application.yml` and `GatewayRoutesConfig`). Postgres: 5432 (user/password/db all `destore` by default).

## Quick API checks (via gateway on 8080)
```bash
# Set price
curl -X POST http://localhost:8080/pricing/sku123 \
  -H "Content-Type: application/json" \
  -d '{"currency":"GBP","amount":9.99}'

# Get price
curl http://localhost:8080/pricing/sku123

# Adjust stock (may trigger low-stock event)
curl -X POST http://localhost:8080/inventory/sku123/adjust \
  -H "Content-Type: application/json" \
  -d '{"delta":-10,"storeId":"store-1"}'

# Finance approval stub
curl -X POST http://localhost:8080/finance/approve \
  -H "Content-Type: application/json" \
  -d '{"customerId":"c1","amount":500}'
```

## Notes / Next steps
- Events use Kafka topics `price-changed` and `low-stock`; ensure a broker is running.
- Services now connect to Postgres at `jdbc:postgresql://localhost:5432/destore` (override via `DB_HOST/DB_PORT/DB_NAME/DB_USER/DB_PASSWORD`).
- Replace the finance stub with an actual Enabling API client.
- Add simple stubs for loyalty/reporting if desired.
- Adapt/extend these docs and code for your coursework, and cite any AI assistance per your rules.
