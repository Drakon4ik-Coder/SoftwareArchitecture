# Finance Gateway Service

Stub adapter to the external Enabling finance system. Returns simple approve/refer decisions.

## Run
```bash
mvn -pl finance-gateway-service spring-boot:run -Dmaven.repo.local=.m2repo
```
Port: 8083.

## API sample
```bash
curl -X POST http://localhost:8083/finance/approve \
  -H "Content-Type: application/json" \
  -d '{"customerId":"c1","amount":500}'
```

## Notes
- Uses a stubbed decision; replace `FinanceGatewayService` with a real REST/SOAP client for Enabling.
- No persistence needed for the prototype.
- Cite AI assistance per coursework rules.
