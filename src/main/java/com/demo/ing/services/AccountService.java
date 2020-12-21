package com.demo.ing.services;

import com.demo.ing.entities.AccountEntity;
import com.demo.ing.entities.UserEntity;
import com.demo.ing.repository.AccountsRepository;
import com.demo.ing.repository.UserRepository;
import com.demo.ing.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    Logger LOG = LoggerFactory.getLogger(AccountService.class);
    AccountsRepository accountsRepository;
    UserRepository userRepository;

    public AccountService(AccountsRepository accountsRepository, UserRepository userRepository) {
        this.accountsRepository = accountsRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity createAccount(String username, Integer ssn) {
        LOG.trace("Entered createAccount");
        AccountEntity foundAccountBySsn = accountsRepository.findByUser_Ssn(ssn);
        if (null != foundAccountBySsn) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(String.format("User %s already has an account", username));
        }

        UserEntity userEntity = new UserEntity(username, ssn, Utils.generateIban());
        this.userRepository.save(userEntity);

        AccountEntity accountEntity = new AccountEntity(userEntity);
        accountEntity.setActive(true);
        this.accountsRepository.save(accountEntity);
        LOG.trace("Exited createAccount");
        return ResponseEntity.ok(accountEntity);

    }

    public ResponseEntity<String> closeAccount(String username, Integer ssn) {
        LOG.trace("Entered closeAccount");
        AccountEntity accountEntityFound = accountsRepository.findByUser_NameAndUser_Ssn(username, ssn);
        if (accountEntityFound == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        }
        accountEntityFound.setActive(false);
        accountsRepository.save(accountEntityFound);
        LOG.trace("Exited closeAccount");
        return ResponseEntity.ok("Account closed");
    }

    public ResponseEntity deleteAccount(String username, Integer ssn) {
        LOG.trace("Entered deleteAccount");
        this.accountsRepository.deleteByUserNameAndUserSsn(username, ssn);
        LOG.trace("Exited deleteAccount");
        return ResponseEntity.ok("Account deleted");
    }

    public ResponseEntity<List<AccountEntity>> getAllAccounts(Boolean active) {
        LOG.trace("Entered getAllAccounts");
        List<AccountEntity> accountEntityList = null;
        if (null == active) {
            accountEntityList = this.accountsRepository.findAll();
        } else {
            accountEntityList = this.accountsRepository.findByActive(active);
        }

        LOG.trace("Exited getAllAccounts");
        return ResponseEntity.ok(accountEntityList);
    }

    public ResponseEntity<AccountEntity> getAccount(String username, Integer ssn) {
        LOG.trace("Entered getAccount");
        AccountEntity accountEntity = this.accountsRepository.findByUser_NameAndUser_Ssn(username, ssn);

        LOG.trace("Exited getAccount");
        return ResponseEntity.ok(accountEntity);
    }

}
