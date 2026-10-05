package org.example.idf_service.repository;
import org.example.idf_service.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.math.BigDecimal; import java.time.*; import java.util.*;
public interface TransactionRepository extends JpaRepository<ExpenseTransaction,Long> {
 @Query("select coalesce(sum(t.amountUsd), 0) from ExpenseTransaction t where t.accountFrom=:account and t.category=:category and t.limit.id=:limitId and t.occurredAt >= :start and t.occurredAt < :end") BigDecimal sumForLimitMonth(@Param("account") String account, @Param("category") ExpenseCategory category, @Param("limitId") Long limitId, @Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);
 @Query(value="""
 select t.* from expense_transactions t join expense_limits l on l.id=t.limit_id
 join (select limit_id, count(*) as exceeded_count from expense_transactions where limit_exceeded=true group by limit_id) x on x.limit_id=l.id
 where t.limit_exceeded=true and t.account_from=:account order by t.occurred_at
 """, nativeQuery=true) List<ExpenseTransaction> findExceededWithLimitAggregate(@Param("account") String account);
}
