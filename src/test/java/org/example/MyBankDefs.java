package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MyBankDefs {
    TestableBank bank = new TestableBank();
    Map<String, UUID> accountIds = new HashMap<>();
    Map<String, Account> accounts = new HashMap<>();

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

    @And("an access key {word} for {word} with access type owner")
    public void anAccessKeyKeyForAccWithAccessTypeOwner(int arg0, int arg1) {
    }
}
