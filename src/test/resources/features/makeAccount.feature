Feature: Create new accounts

  Users should be able to create new accounts and obtain access keys for them.

  Scenario: There are no accounts to begin with
    Then there should be 0 accounts

    # Talk about why these are bad scenarios
  Scenario Outline: Can create accounts with given initial balance
    When I ask to create an account acc1 with initial balance $<initialBalance>
    # Change to "there should be"
    Then there should be 1 accounts
    And the account acc1 should have balance $<initialBalance>

    Examples:
      | initialBalance |
      | 100            |
      | 234            |
      | 315            |

  Scenario: Multiple accounts
    When I ask to create an account acc1 with initial balance $230
    And  I ask to create an account acc2 with initial balance $467
    Then there should be 2 accounts
    And the account acc1 should have balance $230
    And the account acc2 should have balance $467
