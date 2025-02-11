package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank {
    final Map<UUID, Account> accounts = new HashMap<>();
    final Map<UUID, AccessKey> accessKeys = new HashMap<>();

    public Bank() {
    }

    AccessKey createAccessKey(Account account, AccessType type) {
        AccessKey accessKey = new AccessKey(account, type);
        accessKeys.put(accessKey.getKey(), accessKey);
        return accessKey;
    }

    AccessKey retrieveAccessKey(UUID key) throws BankException {
        if (!accessKeys.containsKey(key)) {
            throw BankException.unknownKey();
        }
        return accessKeys.get(key);
    }

    void deleteAccessKey(UUID keyToRevoke) {
        accessKeys.remove(keyToRevoke);
    }

    Account createAndStoreAccount(double initialBalance) {
        Account account = new Account(initialBalance);
        accounts.put(account.getId(), account);
        return account;
    }
}