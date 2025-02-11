package org.example;

import java.util.UUID;

class Account {
    private UUID id;
    private double balance;

    Account() {
        this(0);
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
