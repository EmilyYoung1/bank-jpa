package org.example;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TellerTest {
    Bank bank = new Bank();
    Teller teller = new Teller(bank);

    @Test
    void canCreateAccountWithInitialBalance() throws BankException {
        UUID account1 = teller.createAccount(100);
        assertEquals(100, teller.balance(account1));
    }

    @Test
    void ownerCanDepositAndWithdraw() throws BankException {
        UUID account1 = teller.createAccount(100);
        teller.deposit(account1, 30);
        assertEquals(100 + 30, teller.balance(account1));
        teller.withdraw(account1, 50);
        assertEquals(100 + 30 - 50, teller.balance(account1));
    }

    @Test
    void cannotOverdraw() {
        UUID account1 = teller.createAccount(100);
        try {
            teller.withdraw(account1,100 + 30);
            fail("Should have had an exception for insufficient funds");
        } catch (BankException e) {
            assertEquals(e.type, BankException.Type.InsufficientFunds);
        }
    }

    @Test
    void ownersCanGrantAccess() throws BankException {
        UUID account1 = teller.createAccount(100);
        UUID canOnlyDeposit = teller.grantAccess(account1, AccessType.DEPOSIT);
        UUID canOnlyWithdraw = teller.grantAccess(account1, AccessType.WITHDRAW);
        teller.deposit(canOnlyDeposit, 30);
        teller.withdraw(canOnlyWithdraw, 50);
        try {
            teller.withdraw(canOnlyDeposit, 30);
        } catch (BankException e) {
            assertEquals(e.type, BankException.Type.UnauthorizedOperation);
        }
        try {
            teller.deposit(canOnlyWithdraw, 30);
        } catch (BankException e) {
            assertEquals(e.type, BankException.Type.UnauthorizedOperation);
        }

    }
}