# Expense Limit Service — agent instructions

- Java 21 and Spring Boot 3.5. Run `./mvnw verify` before committing.
- Keep REST DTOs as records in `dto`; entities stay in `domain`; controllers do not contain business logic.
- Create schema changes only as immutable, sequential Flyway files in `src/main/resources/db/migration`.
- Monthly accounting uses `rates.month-zone` (default `Asia/Almaty`) and serializes same account/category/month through `account_month_locks`; do not replace this with JVM-only locking.
- Preserve the RFC 9457 `ProblemDetail` response convention in `web/ApiExceptionHandler`.
