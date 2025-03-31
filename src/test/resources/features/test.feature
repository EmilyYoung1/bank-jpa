Feature: Cucumber run test

  I want to run a sample feature file.

  Scenario: Sum of two numbers
    Given x is equal to 5
    Given y is equal to 4
    When we add x and y
    Then the result is 9

  Scenario: Sum of two other numbers
    Given z is equal to 10
    Given w is equal to 3
    When we add z and w
    Then the result is 13