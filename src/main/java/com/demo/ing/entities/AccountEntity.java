package com.demo.ing.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
public class AccountEntity {
    public AccountEntity() {
    }

    public AccountEntity(UserEntity user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @Column(name = "transaction_id")
    @JsonIgnore
    private List<TransactionEntity> transactions;

    @Column(name = "active")
    private boolean active;

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "user=" + user +
                ", transactions=" + transactions +
                ", active=" + active +
                '}';
    }
}
