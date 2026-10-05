package org.example.idf_service.dto;
import jakarta.validation.constraints.*; import org.example.idf_service.domain.ExpenseCategory; import java.math.BigDecimal;
public record LimitRequest(@NotBlank @Pattern(regexp="\\d{10}") String account_from, @NotNull ExpenseCategory expense_category, @NotNull @DecimalMin(value="0.01") @Digits(integer=17,fraction=2) BigDecimal limit_sum) {}
