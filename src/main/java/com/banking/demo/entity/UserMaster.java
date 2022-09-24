package com.banking.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserMaster extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_master_seq")
    @SequenceGenerator(name = "user_master_seq", sequenceName = "user_master_seq", allocationSize = 1)
    @Column(name = "id_key", unique = true, updatable = false)
    private long idKey;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "mobile_no")
    private String mobileNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
}
