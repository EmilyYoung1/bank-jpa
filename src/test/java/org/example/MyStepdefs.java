package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MyStepdefs {
    Map<String, Integer> storage = new HashMap<>();
    Integer result = null;
    @Given("{word} is equal to {int}")
    public void x_is_equal_to(String varName, Integer varValue) {
        storage.put(varName, varValue);
    }
    @When("we add {word} and {word}")
    public void we_add_two_numbers(String varName1, String varName2) {
        if (storage.containsKey(varName1) && storage.containsKey(varName2)) {
            result = storage.get(varName1) + storage.get(varName2);
        } else {
            fail("I don't know these variables.");
        }
    }
    @Then("the result is {int}")
    public void the_result_is(Integer expected) {
        assertEquals(result, expected);
    }
}
