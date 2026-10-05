package org.example.idf_service.repository;
import org.example.idf_service.domain.*; import org.springframework.data.jpa.repository.*; import java.time.OffsetDateTime; import java.util.*;
public interface LimitRepository extends JpaRepository<ExpenseLimit,Long> { Optional<ExpenseLimit> findFirstByAccountFromAndCategoryAndEstablishedAtLessThanEqualOrderByEstablishedAtDesc(String account, ExpenseCategory category, OffsetDateTime at); List<ExpenseLimit> findByAccountFromOrderByEstablishedAtDesc(String account); }
