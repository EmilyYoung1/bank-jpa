package org.example;

import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

public class MyBankDefs {
    TestableBank bank = new TestableBank();
    @Then("there are {int} accounts")
    public void thereAreAccounts(int expected) {
        int actual = bank.getAdminTeller().getAllAccounts().size();
        assertEquals(actual, expected);
    }

}
