package org.example.idf_service.domain;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="account_month_locks") public class AccountMonthLock { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="account_from") private String accountFrom; @Column(name="month_start") private LocalDate monthStart; @Enumerated(EnumType.STRING) private ExpenseCategory category; protected AccountMonthLock(){} public AccountMonthLock(String a,LocalDate m,ExpenseCategory c){accountFrom=a;monthStart=m;category=c;} }
