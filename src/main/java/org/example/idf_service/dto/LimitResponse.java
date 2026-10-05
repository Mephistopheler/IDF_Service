package org.example.idf_service.dto; import org.example.idf_service.domain.ExpenseCategory; import java.math.BigDecimal; import java.time.OffsetDateTime;
public record LimitResponse(String account_from, ExpenseCategory expense_category, BigDecimal limit_sum, String limit_currency_shortname, OffsetDateTime limit_datetime) {}
