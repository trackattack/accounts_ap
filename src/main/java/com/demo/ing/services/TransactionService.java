package com.demo.ing.services;

import com.demo.ing.entities.AccountEntity;
import com.demo.ing.entities.TransactionEntity;
import com.demo.ing.exceptions.IbanException;
import com.demo.ing.repository.AccountsRepository;
import com.demo.ing.repository.TransactionsRepository;
import com.demo.ing.utils.Utils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    TransactionsRepository transactionsRepository;
    AccountsRepository accountsRepository;
    AccountService accountService;

    public TransactionService(TransactionsRepository transactionsRepository, AccountsRepository accountsRepository, AccountService accountService) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
        this.accountService = accountService;
    }

    public ResponseEntity getTransactions(String username, Integer ssn, Integer previousDays, Integer previousHours, Pageable paging) {
        List<TransactionEntity> userTransactions = Collections.emptyList();
        if (null != previousDays || null != previousHours) {
           /* if (null != previousDays) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, -previousDays);
                Date date = cal.getTime();
                userTransactions = transactionsRepository.findAllWithCreationDateTimeBefore(ssn,username, date, paging).getContent();
            } else {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, -previousHours);
                Date date = cal.getTime();
                userTransactions = transactionsRepository.findAllWithCreationDateTimeBefore(ssn,username, date,paging).getContent();
            }*/

            Calendar cal = Calendar.getInstance();
            if (null != previousDays)
                cal.add(Calendar.DAY_OF_YEAR, -previousDays);
            if (null != previousHours)
                cal.add(Calendar.HOUR, -previousHours);
            Date date = cal.getTime();
            userTransactions = transactionsRepository.findAllWithCreationDateTimeBefore(ssn, username, date, paging).getContent();


        } else {
            userTransactions = transactionsRepository.findAllByAccount_User_SsnAndAccount_User_Name(ssn,username, paging).getContent();
        }
        return ResponseEntity.ok(userTransactions);
    }

    public ResponseEntity createTransaction(String userName, Integer ssn, BigDecimal amount, String receiverIban, boolean createAccount) {
        try {
            TransactionEntity transactionEntity = new TransactionEntity();
            AccountEntity accountEntityFound = accountsRepository.findByUser_Ssn(ssn);

            if (null == accountEntityFound) {
                if (false == createAccount) {
                    return ResponseEntity.badRequest().body(String.format("No account found for user %s with ssn %s", userName, ssn));
                } else {
                    ResponseEntity createAccountResponse = accountService.createAccount(userName, ssn);
                    if (createAccountResponse.getStatusCode().isError()) return createAccountResponse;

                    accountEntityFound = (AccountEntity) createAccountResponse.getBody();
                }

            }
            // transactionEntity.setAccount(accountEntityFound);
            transactionEntity.setAccount(accountEntityFound);


            if (Utils.validateAmount(amount)) {
                transactionEntity.setAmount(amount);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The amount value is not correct");
            }

            if (Utils.validateIban(receiverIban)) {
                transactionEntity.setReceiverIban(Utils.sanitizeIban(receiverIban));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The IBAN value is not correct");

            }

            transactionsRepository.save(transactionEntity);
            return ResponseEntity.ok(transactionEntity);
        } catch (IbanException ibanException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Iban validation encountered an error");
        }
    }
}
