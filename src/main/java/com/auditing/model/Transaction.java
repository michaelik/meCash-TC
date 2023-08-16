package com.auditing.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    Long transactionId;
    @Column(
            name = "source_account",
            nullable = false
    )
    String debitAccount;
    @Column(
            name = "recipient_name",
            nullable = false
    )
    String recipientName;
    @Column(
            name = "recipient_account",
            nullable = false
    )
    String recipientAccount;
    @Column(
            name = "amount"
    )
    BigDecimal amount;
    @Column(
            name = "user_current_balance"
    )
    BigDecimal currentBalance;
    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    User user;
    @ManyToOne(
            targetEntity = Account.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_account_id",
            referencedColumnName = "account_id"
    )
    Account account;
    @Builder.Default
    @CreationTimestamp
    @Column(
            name = "createdAt",
            columnDefinition = "TIMESTAMP NOT NULL"
    )
    LocalDateTime timestamp = LocalDateTime.now();
}
