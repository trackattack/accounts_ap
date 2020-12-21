package com.demo.ing.repository;

import com.demo.ing.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountsRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUser_Name(String username);

    AccountEntity findByUser_Ssn(Integer ssn);

    AccountEntity findByUser_NameAndUser_Ssn(String username, Integer ssn);

    @Transactional
    @Modifying
    void deleteByUserNameAndUserSsn(String username, Integer ssn);

    List<AccountEntity> findByActive(boolean status);
}
