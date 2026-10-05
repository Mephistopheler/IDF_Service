package org.example.idf_service.domain;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="expense_limits") public class ExpenseLimit {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="account_from", nullable=false) private String accountFrom;
 @Enumerated(EnumType.STRING) @Column(name="category",nullable=false) private ExpenseCategory category; @Column(name="amount_usd",nullable=false) private BigDecimal amountUsd; @Column(name="established_at",nullable=false) private OffsetDateTime establishedAt;
 protected ExpenseLimit() {} public ExpenseLimit(String accountFrom, ExpenseCategory category, BigDecimal amountUsd, OffsetDateTime establishedAt) { this.accountFrom=accountFrom;this.category=category;this.amountUsd=amountUsd;this.establishedAt=establishedAt; }
 public Long getId(){return id;} public String getAccountFrom(){return accountFrom;} public ExpenseCategory getCategory(){return category;} public BigDecimal getAmountUsd(){return amountUsd;} public OffsetDateTime getEstablishedAt(){return establishedAt;}
}
