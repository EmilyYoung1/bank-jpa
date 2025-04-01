package org.example;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jdk.internal.access.JavaSecurityAccess;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MyBankDefs {
    TestableBank bank = new TestableBank();
    Map<String, UUID> accountIds = new HashMap<>();
    Map<String, Account> accounts = new HashMap<>();
    Map<String, AccessKey> accessKeys = new HashMap<>();
    BankException.Type lastTransactionError = null;

    @ParameterType("owner|withdraw|deposit")
    AccessType accessType(String s) {
        if (s.equals("owner")) return AccessType.OWNER;
        if (s.equals("withdraw")) return AccessType.WITHDRAW;
        if (s.equals("deposit")) return AccessType.DEPOSIT;
        throw new RuntimeException("Unknown access type: " + s);
    }

    @Then("there should be {long} accounts")
    public void thereAreAccounts(long expected) {
        long actual = bank.getAdminTeller().getNumberOfAccounts();
        assertEquals(expected, actual);
    }

    @When("I ask to create an account {word} with initial balance ${int}")
    public void askToCreateAnAccount(String accountName, int initialBalance) {
        UUID uuid = bank.getTeller().createAccount(initialBalance);
        accountIds.put(accountName, uuid);
    }

    @And("the account {word} should have balance ${int}")
    public void theAccountHasBalance(String accountName, int balance) throws BankException {
        UUID uuid = accountIds.get(accountName);
        double actual = bank.getTeller().balance(uuid);
        assertEquals(balance, actual, 1e-10);
    }

    @Given("an account {word} with balance ${int}")
    public void anAccountWithBalance$(String accountName, int initialBalance) {
        Account acc = bank.getAdminTeller().makeRawAccount(initialBalance);
        accounts.put(accountName, acc);
    }

    @And("an access key {word} for {word} with access type {accessType}")
    public void accessKeyForAccount(String accessKeyName, String accountName, AccessType accessType) {
        Account account = accounts.get(accountName);
        if (account == null) throw new RuntimeException("No account with that name");
        AccessKey accessKey = bank.getAdminTeller().makeRawAccessKey(account, accessType);
        accessKeys.put(accessKeyName, accessKey);
    }

    @When("I withdraw ${int} using access key {word}")
    public void iWithdrawUsingAccessKey(int amount, String accessKeyName) {
        AccessKey accessKey = accessKeys.get(accessKeyName);
        try {
            bank.getTeller().withdraw(accessKey.getKey(), amount);
            lastTransactionError = null;
        } catch (BankException e) {
            lastTransactionError = e.type;
        }
    }

    @Then("The transaction was OK")
    public void theTransactionWasOK() {
        assertNull(lastTransactionError);
    }
}
