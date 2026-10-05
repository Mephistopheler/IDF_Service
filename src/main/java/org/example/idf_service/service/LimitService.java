package org.example.idf_service.service;
import org.example.idf_service.domain.*; import org.example.idf_service.dto.*; import org.example.idf_service.repository.LimitRepository; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.math.*; import java.time.*; import java.util.*;
@Service public class LimitService { private final LimitRepository limits; private final Clock clock; public LimitService(LimitRepository limits,Clock clock){this.limits=limits;this.clock=clock;}
 @Transactional public LimitResponse create(LimitRequest request){ OffsetDateTime now=OffsetDateTime.now(clock); ExpenseLimit l=limits.save(new ExpenseLimit(request.account_from(),request.expense_category(),request.limit_sum().setScale(2,RoundingMode.HALF_UP),now)); return map(l); }
 @Transactional(readOnly=true) public List<LimitResponse> all(String account){return limits.findByAccountFromOrderByEstablishedAtDesc(account).stream().map(this::map).toList();}
 public ExpenseLimit applicable(String account,ExpenseCategory category,OffsetDateTime occurred){ return limits.findFirstByAccountFromAndCategoryAndEstablishedAtLessThanEqualOrderByEstablishedAtDesc(account,category,occurred).orElseGet(()->limits.save(new ExpenseLimit(account,category,new BigDecimal("1000.00"),OffsetDateTime.ofInstant(Instant.EPOCH,ZoneOffset.UTC)))); }
 private LimitResponse map(ExpenseLimit l){return new LimitResponse(l.getAccountFrom(),l.getCategory(),l.getAmountUsd(),"USD",l.getEstablishedAt());}
}
