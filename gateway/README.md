# Gateway

Spring Cloud Gateway entry point that routes external traffic to internal services.

## Run
```bash
mvn -pl gateway spring-boot:run -Dmaven.repo.local=.m2repo
```

Default port: 8080. Routes defined in `GatewayRoutesConfig.java` point to:
- pricing: http://localhost:8081
- inventory: http://localhost:8082
- finance: http://localhost:8083
- notification: http://localhost:8084

Adjust target URIs if ports/hosts change.

## Quick check
With services running, try `curl http://localhost:8080/pricing/sku123` (after setting a price).

Adapt as needed and cite AI assistance per your coursework rules.
