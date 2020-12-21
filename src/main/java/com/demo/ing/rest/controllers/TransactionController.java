package com.demo.ing.rest.controllers;

import com.demo.ing.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {
    Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestParam(value = "username") String username,
                                            @RequestParam(value = "ssn") Integer ssn,
                                            @RequestParam(value = "amount") BigDecimal amount,
                                            @RequestParam(value = "receiverIban") String receiverIban,
                                            @RequestParam(value = "createAccount", defaultValue = "false") boolean createAccount) {
        LOG.trace("Entered createTransaction");
        return transactionService.createTransaction(username, ssn, amount, receiverIban, createAccount);
    }

    @GetMapping("/transactions")
    public ResponseEntity getUserTransactions(@RequestParam(value = "username") String username,
                                              @RequestParam(value = "ssn") Integer ssn,
                                              @RequestParam(value = "previousDays", required = false) Integer previousDays,
                                              @RequestParam(value = "previousHours", required = false) Integer previousHours,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        LOG.trace("Entered getUserTransactions");
        Pageable paging = (Pageable) PageRequest.of(page, size);
        return transactionService.getTransactions(username, ssn, previousDays, previousHours, paging);
    }
}
