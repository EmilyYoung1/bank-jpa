package org.example;

import jakarta.persistence.EntityManager;

import java.util.UUID;

import static org.example.AccessType.OWNER;

// TODOS:
// 1. Transaction log
// 2. View all access keys for account
// 3. "Close account"
// 4. Move entity manager stuff to Bank/Teller

public class Teller implements AutoCloseable {
    private final EntityManager em;

    public Teller(EntityManager em) {
        this.em = em;
    }

    public void close() {
        em.close();
    }

    UUID createAccount(double initialBalance) {
        Account account = new Account(initialBalance);
        AccessKey accessKey = new AccessKey(account, OWNER);
        em.getTransaction().begin();
        em.persist(account);
        em.persist(accessKey);
        em.getTransaction().commit();

        return accessKey.getKey();
    }

    double balance(UUID key) throws BankException {
        AccessKey accessKey = getAccessKey(key);
        if (!accessKey.canWithdraw()) throw BankException.unauthorizedOperation();
        return accessKey.getAccount().getBalance();
    }

    private AccessKey getAccessKey(UUID key) {
        AccessKey accessKey = em.find(AccessKey.class, key);
        if (accessKey == null) throw new RuntimeException("No AccessKey for key " + key);
        return accessKey;
    }

    void deposit(UUID key, double amount) throws BankException {
        AccessKey accessKey = getAccessKey(key);

        if (amount <= 0) throw BankException.invalidAmount();
        if (!accessKey.canDeposit()) throw BankException.unauthorizedOperation();
        em.getTransaction().begin();
        accessKey.getAccount().deposit(amount);
        em.getTransaction().commit();
    }

    void withdraw(UUID key, double amount) throws BankException {
        AccessKey accessKey = getAccessKey(key);
        if (amount <= 0) throw BankException.invalidAmount();
        if (!accessKey.canWithdraw()) throw BankException.unauthorizedOperation();
        Account account = accessKey.getAccount();
        double balance = account.getBalance();
        if (amount > balance) throw BankException.insufficientFunds();
        else {
            em.getTransaction().begin();
            account.withdraw(amount);
            em.getTransaction().commit();
        }
    }

    UUID grantAccess(UUID requesterKey, AccessType requestedAccess) throws BankException {
        AccessKey requester = getAccessKey(requesterKey);
        if (!requester.canGrantAccess()) throw BankException.unauthorizedOperation();
        em.getTransaction().begin();
        AccessKey accessKey = new AccessKey(requester.getAccount(), requestedAccess);
        em.persist(accessKey);
        em.getTransaction().commit();
        return accessKey.getKey();
    }

    void revokeAccess(UUID requesterKey, UUID keyToRevoke) throws BankException {
        AccessKey requester = getAccessKey(requesterKey);
        if (!requester.canGrantAccess()) throw BankException.unauthorizedOperation();
        AccessKey accessKey = getAccessKey(keyToRevoke);
        em.getTransaction().begin();
        em.remove(accessKey);
        em.getTransaction().commit();
    }

}
