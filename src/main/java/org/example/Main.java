package org.example;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Teller teller = new Teller(bank);
        UUID account = teller.createAccount(100);
    }
}