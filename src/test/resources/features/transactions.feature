Feature: Owner can perform transactions

  Background:
    Given an account acc1 with balance $345
    And an account acc2 with balance $156
    And an access key key1 for acc1 with access type owner
    And an access key key2 for acc2 with access type owner

  Scenario: Can withdraw from account
    When I withdraw $100 using access key key1
    Then The transaction was OK