package org.example.bank_system.entity.transaction;


import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.example.bank_system.entity.account.Account;
import org.example.bank_system.entity.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "from_account_id",
            foreignKey = @ForeignKey(name = "fk_transaction_from_account")
    )
    private Account fromAccount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "to_account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction_to_account")
    )
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TransactionStatus status;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type;

    @Column(name = "commission", nullable = false)
    private BigDecimal commission;


    @Column(name = "total_debit", nullable = false)
    private BigDecimal totalDebit;

    @Column(name = "pending_time", nullable = false)
    private LocalDateTime pendingTime;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}



