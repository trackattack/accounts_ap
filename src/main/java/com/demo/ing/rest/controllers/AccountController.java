package com.demo.ing.rest.controllers;

import com.demo.ing.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/account")
    public ResponseEntity createAccount(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "ssn") Integer ssn) {
        LOG.trace("Entered createAccount");
        return accountService.createAccount(username, ssn);
    }

    @GetMapping("/account")
    public ResponseEntity getAccount(@RequestParam(value = "username") String username,
                                     @RequestParam(value = "ssn") Integer ssn) {
        LOG.trace("Entered getAccount");
        return accountService.getAccount(username, ssn);
    }

    @GetMapping("/allAccounts")
    public ResponseEntity getAllAccounts(@RequestParam(value = "active", required = false) Boolean active) {
        LOG.trace("Entered getAllAccounts");
        return accountService.getAllAccounts(active);
    }

    @PostMapping("/closeAccount")
    public ResponseEntity closeAccount(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "ssn") Integer ssn) {
        LOG.trace("Entered closeAccount");
        return accountService.closeAccount(username, ssn);
    }

    @DeleteMapping("/account")
    public ResponseEntity deleteAccount(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "ssn") Integer ssn) {
        LOG.trace("Entered deleteAccount");
        return accountService.deleteAccount(username, ssn);
    }

}
