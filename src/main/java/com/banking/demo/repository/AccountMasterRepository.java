package com.banking.demo.repository;

import com.banking.demo.entity.AccountMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMasterRepository extends JpaRepository<AccountMaster, Long> {

    AccountMaster findByUserMasterIdKey(long userMasterIdKey);
}
