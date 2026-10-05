package org.example.idf_service.dto;
import jakarta.validation.constraints.*; import org.example.idf_service.domain.ExpenseCategory; import java.math.BigDecimal; import java.time.OffsetDateTime;
public record TransactionRequest(@NotBlank @Pattern(regexp="\\d{10}") String account_from, @NotBlank @Pattern(regexp="\\d{10}") String account_to, @NotBlank @Pattern(regexp="[A-Z]{3}") String currency_shortname, @NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal sum, @NotNull ExpenseCategory expense_category, @NotNull OffsetDateTime datetime) {}
