package org.example;

import java.util.UUID;

import static org.example.AccessType.OWNER;

// TODOS:
// 1. Transaction log

public class Teller {
    private final Bank bank;

    public Teller(Bank bank) {
        this.bank = bank;
    }

    UUID createAccount(double initialBalance) {
        Account account = bank.createAndStoreAccount(initialBalance);
        AccessKey accessKey = bank.createAccessKey(account, OWNER);
        return accessKey.getKey();
    }

    double balance(UUID key) throws BankException {
        AccessKey accessKey = bank.retrieveAccessKey(key);
        if (!accessKey.canWithdraw()) throw BankException.unauthorizedOperation();
        return accessKey.getAccount().getBalance();
    }

    void deposit(UUID key, double amount) throws BankException {
        AccessKey accessKey = bank.retrieveAccessKey(key);
        if (amount <= 0) throw BankException.invalidAmount();
        if (!accessKey.canDeposit()) throw BankException.unauthorizedOperation();
        accessKey.getAccount().deposit(amount);
    }

    void withdraw(UUID key, double amount) throws BankException {
        AccessKey accessKey = bank.retrieveAccessKey(key);
        if (amount <= 0) throw BankException.invalidAmount();
        if (!accessKey.canWithdraw()) throw BankException.unauthorizedOperation();
        Account account = accessKey.getAccount();
        double balance = account.getBalance();
        if (amount > balance) throw BankException.insufficientFunds();
        else account.withdraw(amount);
    }

    UUID grantAccess(UUID requesterKey, AccessType requestedAccess) throws BankException {
        AccessKey requester = bank.retrieveAccessKey(requesterKey);
        if (!requester.canGrantAccess()) throw BankException.unauthorizedOperation();
        AccessKey accessKey = bank.createAccessKey(requester.getAccount(), requestedAccess);
        return accessKey.getKey();
    }

    void revokeAccess(UUID requesterKey, UUID keyToRevoke) throws BankException {
        AccessKey requester = bank.retrieveAccessKey(requesterKey);
        if (!requester.canGrantAccess()) throw BankException.unauthorizedOperation();
        bank.deleteAccessKey(keyToRevoke);
    }

}
