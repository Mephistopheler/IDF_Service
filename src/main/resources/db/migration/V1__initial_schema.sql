CREATE TABLE expense_limits (
 id BIGSERIAL PRIMARY KEY, account_from VARCHAR(10) NOT NULL, category VARCHAR(16) NOT NULL,
 amount_usd NUMERIC(19,2) NOT NULL CHECK (amount_usd > 0), established_at TIMESTAMP WITH TIME ZONE NOT NULL,
 CONSTRAINT uq_limit_version UNIQUE (account_from, category, established_at)
);
CREATE INDEX idx_limits_lookup ON expense_limits(account_from, category, established_at DESC);
CREATE TABLE expense_transactions (
 id BIGSERIAL PRIMARY KEY, account_from VARCHAR(10) NOT NULL, account_to VARCHAR(10) NOT NULL,
 currency VARCHAR(3) NOT NULL, amount NUMERIC(19,2) NOT NULL CHECK (amount > 0), category VARCHAR(16) NOT NULL,
 occurred_at TIMESTAMP WITH TIME ZONE NOT NULL, amount_usd NUMERIC(19,2) NOT NULL, limit_exceeded BOOLEAN NOT NULL,
 limit_id BIGINT REFERENCES expense_limits(id)
);
CREATE INDEX idx_transactions_limit ON expense_transactions(account_from, category, occurred_at);
CREATE TABLE exchange_rates (
 id BIGSERIAL PRIMARY KEY, currency VARCHAR(3) NOT NULL, rate_date DATE NOT NULL,
 usd_rate NUMERIC(19,8) NOT NULL CHECK (usd_rate > 0), source VARCHAR(64) NOT NULL,
 CONSTRAINT uq_rate_currency_date UNIQUE(currency, rate_date)
);
CREATE TABLE account_month_locks (id BIGSERIAL PRIMARY KEY, account_from VARCHAR(10) NOT NULL, month_start DATE NOT NULL, category VARCHAR(16) NOT NULL, CONSTRAINT uq_account_month_lock UNIQUE(account_from, month_start, category));
