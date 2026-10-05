package org.example.idf_service.mapper;
import org.example.idf_service.domain.ExpenseLimit; import org.example.idf_service.dto.LimitResponse; import org.mapstruct.*;
@Mapper(componentModel = "spring") public interface LimitMapper { @Mapping(target="limit_sum", source="amountUsd") @Mapping(target="limit_datetime", source="establishedAt") @Mapping(target="limit_currency_shortname", constant="USD") LimitResponse toResponse(ExpenseLimit limit); }
