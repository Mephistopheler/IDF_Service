# Expense Limit Service

Prototype Spring Boot 3.5 / Java 21 service for recording expense transactions and applying per-account USD monthly limits. **Estimate:** 24–32 hours for the required implementation, test hardening, documentation and review.

## Run

Prerequisites: Java 21 and PostgreSQL. Create an `expenses` database and run:

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/expenses DATABASE_USER=expenses DATABASE_PASSWORD=expenses ./mvnw spring-boot:run
```

The Flyway migration runs automatically. API documentation is at `/swagger-ui/index.html`; OpenAPI JSON is at `/v3/api-docs`. Verify with `./mvnw verify`.

## APIs

* `POST /api/transactions` accepts an expense transaction.
* `POST /api/clients/limits` creates an immutable limit; service time is set by `Clock`.
* `GET /api/clients/{account}/limits` lists the account's limit history.
* `GET /api/clients/{account}/transactions/limit-exceeded` lists exceeded transactions with their applicable limit.

All invalid payloads use RFC 9457 `ProblemDetail`. Dates are ISO-8601 offset date-times, category is `PRODUCT` or `SERVICE`.

## Design and correctness

The PostgreSQL schema is normalized into limits, transactions, exchange rates, and account-month lock rows. Limits are versioned: an expense uses the newest limit whose establishment time is no later than its own timestamp; absent history creates an immutable 1000.00 USD baseline. Each transaction is converted with the requested-date cached rate, or the nearest preceding close. The remote Frankfurter provider is invoked only on a cache miss.

Month boundaries use `Asia/Almaty` by default (`MONTH_ZONE` overrides it), regardless of the transaction's supplied offset. A pessimistic database lock on `(account, category, month)` serializes concurrent writes, then the service sums spending only for the applicable limit version. This prevents concurrent requests from consuming the same remaining amount. The exceeded-list repository query deliberately uses a `JOIN` with a grouped aggregate subquery, as required.

Remote rate failures result in `503` and no transaction is persisted because conversion and persistence are one transaction. Clients can safely retry; this favors correctness and no partial data over accepting an unpriced transaction. Production deployment should add an outbox/retry queue if upstream delivery cannot retry.

## AI in this project

Codex was used to scaffold and review implementation; every generated query, lock scope and DTO constraint was manually checked. Context7 is configured in `.codex/config.toml` for current Spring documentation; it is useful when checking a Spring API or migration integration and stores no secrets. Apply the project skill at `.agents/skills/add-exchange-rate-provider/SKILL.md` when changing the rate provider. The baseline domain model and invariants were written and reviewed manually. A common AI mistake is suggesting a JVM `synchronized` lock, which is incorrect across service instances; this project uses a row lock in PostgreSQL instead.
