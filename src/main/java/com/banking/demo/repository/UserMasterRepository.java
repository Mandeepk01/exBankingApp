package com.banking.demo.repository;

import com.banking.demo.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

    boolean existsByUserIdIgnoreCase(String userId);
    UserMaster findByUserIdIgnoreCase(String userId);
}
