---
name: add-exchange-rate-provider
description: Use when adding or replacing the remote USD exchange-rate source in the Expense Limit Service, including response parsing, caching, failures, and tests.
---

1. Keep the public calculation contract in `service/RateService#usdRate(String, LocalDate)`: it returns USD per one unit of the input currency and returns `1` for USD.
2. Add provider-specific HTTP parsing behind a small client in `client/`; configure its base URL and timeouts under `rates` in `application.yml` via `RatesProperties`. Do not commit API keys; read them from environment-backed configuration.
3. Check `RateRepository.findFirstByCurrencyAndRateDateLessThanEqualOrderByRateDateDesc` before a remote call and persist a successful result as `ExchangeRate`. This fallback is what covers non-trading days.
4. On provider failure, throw `RateUnavailableException`; the central handler turns it into RFC 9457 `503` rather than saving a transaction with an invented rate.
5. Add unit tests for cached rate, USD identity, successful provider parsing, and provider failure. Run `./mvnw verify`.
