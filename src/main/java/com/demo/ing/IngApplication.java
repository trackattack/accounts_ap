package com.demo.ing;

import com.demo.ing.repository.AccountsRepository;
import com.demo.ing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IngApplication /*extends SpringBootServletInitializer*/ {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountsRepository accountsRepository;

    @PostConstruct
    public void init() {
    }

    public static void main(String[] args) {
        SpringApplication.run(IngApplication.class, args);
    }

}
