package org.example;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="accounts")
class Account {
    @Id
    UUID id;
    @Basic(optional = false)
    double balance;

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
