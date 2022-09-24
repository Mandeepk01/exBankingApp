package com.banking.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "account_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountMaster extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_master_seq")
    @SequenceGenerator(name = "account_master_seq", sequenceName = "account_master_seq", allocationSize = 1)
    @Column(name = "id_key", unique = true, updatable = false)
    private long idKey;
    @Column(name = "user_master_id_key")
    private long userMasterIdKey;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "currency")
    private String currency;
}
