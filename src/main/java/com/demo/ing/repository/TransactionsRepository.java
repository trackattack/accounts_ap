package com.demo.ing.repository;

import com.demo.ing.entities.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {

    Page<TransactionEntity> findAllByAccount_User_SsnAndAccount_User_Name(Integer ssn, String username, Pageable pageable);

    @Query("select t from TransactionEntity t where t.account.user.ssn= :ssn  AND t.account.user.name= :username AND t.creationDate <= :creationDateTime")
    Page<TransactionEntity> findAllWithCreationDateTimeBefore(
            @Param("ssn") Integer ssn,
            @Param("username") String username,
            @Param("creationDateTime") Date creationDateTime,
            Pageable pageable);
}
