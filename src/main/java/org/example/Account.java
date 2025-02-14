package org.example;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="accounts")
class Account {
    @Id
    UUID id;
    @Basic(optional = false)
    double balance;
    @OneToMany(mappedBy = "account", fetch=FetchType.LAZY)
    List<AccessKey> accessKeys;

    protected Account() {
    }

    Account(double initialBalance) {
        id = UUID.randomUUID();
        balance = initialBalance;
    }

    double getBalance() {
        return balance;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
    }

    UUID getId() {
        return id;
    }

    void withdraw(double amount) {
        balance -= amount;
    }
}
