package org.example;

import jakarta.persistence.*;

import java.util.UUID;

import static org.example.AccessType.*;

@Entity
class AccessKey {
    @Id
    @Column(name="id")
//    @Column(name="\"key\"")
    private UUID key;
    @ManyToOne
    private Account account;
    @Basic
    private AccessType accessType;

    protected AccessKey() {

    }

    public AccessKey(Account account, AccessType accessType) {
        key = UUID.randomUUID();
        this.account = account;
        this.accessType = accessType;
    }

    UUID getKey() {
        return key;
    }

    void setKey(UUID key) {
        this.key = key;
    }

    Account getAccount() {
        return account;
    }

    void setAccount(Account account) {
        this.account = account;
    }

    AccessType getAccessType() {
        return accessType;
    }

    void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    boolean canDeposit() {
        return accessType == OWNER || accessType == DEPOSIT;
    }

    boolean canWithdraw() {
        return accessType == OWNER || accessType == WITHDRAW;
    }

    boolean canGrantAccess() {
        return accessType == OWNER;
    }
}
