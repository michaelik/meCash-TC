package com.auditing.model;

import com.auditing.enums.AccountCurrency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    Long accountId;
    @Column(
            name = "account_number",
            nullable = false
    )
    String accountNumber;
    @Column(
            name = "balance",
            precision = 19, scale = 2
    )
    BigDecimal balance;
    @Enumerated(EnumType.STRING)
    AccountCurrency currency;
    @OneToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "fk_user_id",
            referencedColumnName = "id"
    )
    User user;
    @OneToMany(
            targetEntity = Transaction.class,
            cascade = CascadeType.ALL
    )
    List<Transaction> transaction;
}
